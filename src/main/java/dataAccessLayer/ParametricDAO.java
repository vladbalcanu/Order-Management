package dataAccessLayer;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import connection.ConnectionFactory;
import businessLayer.Trivia.Generator;

/**
 * @author Vlad-Andrei Balcanu
 * @param <T> Parametrul trimis ce specifica clasa pe care se lucreaza ( tabelul din ta baza de date corespunzator)
 */
public class ParametricDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(ParametricDAO.class.getName());
    protected Class<T> tableClassType;
    public Generator<T> generator = new Generator<>();

    /**
     *  <p>
     *      Constructorul clasei
     *      Seteaza tipul clasei/tabelei folosite la cel trasmis la apelare
     *  </p>
     * @param tableClassType Tipul clasei cu care lucram
     */
    public ParametricDAO(Class<T> tableClassType){
        this.tableClassType = tableClassType;
    }

    /**
     * <p>
     *
     * </p>
     *
     * @param object
     * @return
     * @throws IllegalAccessException
     */
    public List<Object> getValues(T object) throws IllegalAccessException{
        List<Object> valuesOfClass= new ArrayList<>();
        for(Field field: tableClassType.getDeclaredFields()){
            field.setAccessible(true);
            valuesOfClass.add(field.get(object));
        }
        return valuesOfClass;
    }

    /**
     * <p>
     *     Metoda de generare a datelor dintr-o tabela T
     * </p>
     * @param rs Rezultatele interogarii
     * @return o lista de obiecte de tipul T (colaonele tabelei T)
     */
    private List<T> generateTable(ResultSet rs){
        List<T> objectList= new ArrayList<>();
        List<String> classTypes= generator.generateTableColumnsTypes(tableClassType);
        try{
            while(rs.next()){
                int i=0;
                T instance = tableClassType.newInstance();
                for(Field field: tableClassType.getDeclaredFields()){
                 Object value= rs.getObject(field.getName());
                 PropertyDescriptor propertyDescriptor= new PropertyDescriptor(field.getName(), tableClassType);
                 Method method = propertyDescriptor.getWriteMethod();
                 if(classTypes.get(i).equals("Int")){
                     Integer id= Integer.parseInt(value.toString());
                     method.invoke(instance,id);
                 }else{
                     method.invoke(instance,value);
                 }
                 i++;
                }
                objectList.add(instance);
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        {
        }
    return objectList;
    }

    /**
     * <p>
     *     Metoda pentru afisarea elementelor uneia dintre clase (tabele)
     * </p>
     * @return o lista de obiecte de tipul T (valorile din  campurile din tabela classType)
     * @throws SQLException
     */
    public List<T> showAll() throws SQLException{
        Connection cn=null;
        PreparedStatement st=null;
        ResultSet rs=null;
        String query= "Select * from "+ tableClassType.getSimpleName();
        try{
            cn= ConnectionFactory.getConnection();
            st=cn.prepareStatement(query);
            rs=st.executeQuery();
            return generateTable(rs);

        }catch (SQLException e){
            LOGGER.log(Level.WARNING, tableClassType.getName()+"ParametricDAO:showAll " +e.getMessage());

        }finally{
            ConnectionFactory.close(Objects.requireNonNull(rs));
            ConnectionFactory.close(Objects.requireNonNull(cn));
            ConnectionFactory.close(st);


        }
        return null;
    }


    /**
     * <p>
     *     Metoda de cautare a unui obiect in una dintre cele 3 clase dupa ID
     * </p>
     * @param id id-ul obiectului
     * @return obiectul de tip T gasit
     * @throws SQLException arunca o exceptie daca nu exista obiectuk
     */
    public T findById(int id) throws SQLException {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String query = "Select * from " + tableClassType.getSimpleName() + " where id=?";
        try {
            cn = ConnectionFactory.getConnection();
            st = cn.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            return generateTable(rs).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, tableClassType.getName() + "ParametricDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(Objects.requireNonNull(rs));
            ConnectionFactory.close(Objects.requireNonNull(cn));
            ConnectionFactory.close(st);
        }
        return null;
    }

    /**
     * <p>
     *     Metoda pentru stergerea unui obiect din cele 3 clase
     * </p>
     * @param id id-ul obiectului
     * @return instanta obiectului
     * @throws SQLException arunca o exceptie daca nu exista obiectul
     */
    public T delete(int id) throws SQLException {

        Connection cn = null;
        PreparedStatement st = null;
        String query = "Delete from " + tableClassType.getSimpleName() + " where id=?";
        T instance = findById(id);
        if (instance != null) {
            try {
                cn = ConnectionFactory.getConnection();
                st = cn.prepareStatement(query);
                st.setInt(1, id);
                st.executeUpdate();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, tableClassType.getName() + "ParametricDAO:delete " + e.getMessage());
            } finally {
                ConnectionFactory.close(Objects.requireNonNull(cn));
                ConnectionFactory.close(Objects.requireNonNull(st));
            }
            return instance;
        } else
            return null;
    }

    /**
     * <p>
     *     Metoda folosita pentru adaugarea unui obiect in unul dintre una din cele 3 clase
     * </p>
     * @param object obiectul de tip T ce trebuie adaugat
     * @return obiectul adaugat in tabela / clasa
     * @throws SQLException
     */
    public T add(T object) throws SQLException {

        Connection cn;
        PreparedStatement st;
        List<String> attr = generator.generateTableColumnsNames(tableClassType);
        String query = generator.insertQuery(attr, tableClassType);
        try {
            List<String> types = generator.generateTableColumnsTypes(tableClassType);
            cn = ConnectionFactory.getConnection();
            st = cn.prepareStatement(query);
            List<Object> valuesOfClass = getValues(object);
            for (int i = 1; i < types.size(); i++) {
                if (types.get(i).startsWith("Int")) {
                    st.setInt(i, (Integer) valuesOfClass.get(i));
                } else {
                    st.setString(i, String.valueOf(valuesOfClass.get(i)));
                }
            }
            st.executeUpdate();
            return object;
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, tableClassType.getName() + "DAO:insert " + e.getMessage());
        }
        return null;
    }

    /**
     * <p>
     *     Metoda pentru a actualiza un obiect din una dintre cele 3 clase/tabele
     * </p>
     * @param id id-ul obiectului ce trebuie actualizat
     * @param object obiectul cu valorile ce vor lua locul valorilor vechi
     * @return obiectul actualizat
     * @throws SQLException arunca o exceptie daca nu exista obiectul
     */
    public T update(int id, T object) throws SQLException {

        Connection cn;
        PreparedStatement st;
        List<String> tableAttributes = generator.generateTableColumnsNames(tableClassType);
        String query = generator.updateQuery(tableAttributes, tableClassType);
        try {
            List<String> types = generator.generateTableColumnsTypes(tableClassType);
            cn = ConnectionFactory.getConnection();
            st = cn.prepareStatement(query);
            List<Object> valuesOfClass = getValues(object);
            for (int i = 1; i < types.size(); i++) {
                if (types.get(i).startsWith("Int")) {
                    st.setInt(i, (Integer) valuesOfClass.get(i));
                } else {
                    st.setString(i, String.valueOf(valuesOfClass.get(i)));
                }
            }
            st.setInt(tableAttributes.size(), id);
            st.executeUpdate();
            return object;
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, tableClassType.getName() + "DAO:update " + e.getMessage());
        }
        return null;
    }

}
