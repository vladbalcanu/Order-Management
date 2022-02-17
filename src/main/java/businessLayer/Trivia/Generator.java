package businessLayer.Trivia;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Vlad-Andrei Balcanu
 * @param <T> parametru generic prin care trasnmitem tabela ce trebuie folosita
 */
public class Generator<T> {
    /**
     * <p>
     *     Aceasta metoda imi genereaza tipurile coloanelor tabelului
     * </p>
     * @param type tabelul trimis ca sa ii fie generate tipurile coloanelor (Int , String)
     * @return ArrayList-ul cu tipurile coloanelor( l-am numit table)
     */
    public List<String> generateTableColumnsTypes(@org.jetbrains.annotations.NotNull Class<T> type) {
        ArrayList<String> table = new ArrayList<>();
        for (Field types : type.getDeclaredFields()) {
            table.add(generateColumnType(types.getAnnotatedType().toString()));
        }
        return table;
    }

    /**
     * <p>
     *     Metoda ce primeste tipul adnotat al unei coloane si il returneaza sub forma de String
     *
     * </p>
     * @param receivedType tipul primit
     * @return String cu tipul primit
     */
    public static String generateColumnType(String receivedType){
        if (receivedType.endsWith("String"))
            return "String";
        else
            return "Int";
    }

    /**
     *<p>
     *     Aceasta metoda imi genereaza numele coloanelor tabelului
     *</p>
     * @param type tabelul trimis ca sa ii fie generate numele coloanelor
     * @return returneaza numele coloanelor tabelului
     */
    public List<String> generateTableColumnsNames(Class<T> type) {
        List<String> columns = new ArrayList<>();
        for (Field names : type.getDeclaredFields()) {
            columns.add(names.getName());
        }
        return columns;
    }

    /**
     *<p>
     *     Aceasta metoda imi face un updateQuery (actualizeaza tabela) pe un tabel trimis la coloanele specificate
     *</p>
     * @param columns coloanele care trebuie actualizate
     * @param type tabelul ce trebuie actualizat
     * @return interogarea SQL pentru update pe tabelul trimis
     */

    public String updateQuery(List<String> columns, Class<T> type) {
        StringBuilder toBeFormed = new StringBuilder();
        for (int i = 1; i < columns.size(); i++) {
            if (i == columns.size() - 1) {
                toBeFormed.append(columns.get(i)).append("=? ");
            } else {
                toBeFormed.append(columns.get(i)).append("=?, ");
            }
        }
        return "update " + type.getSimpleName().toLowerCase(Locale.ROOT) + " set " + toBeFormed + " where id = ?";
    }

    /**
     *<p>
     *     Aceasta metoda imi face insertQuery (insereaza in tabela) pe un tabel trimis
     *</p>
     * @param columns ArrayList-ul de coloane in care se introduc datele
     * @param type Tabelul trimis pentru a fi adaugat in el un obiect
     * @return Interogarea SQL pentru insert in tabelul trimis
     */
    public String insertQuery(List<String> columns, Class<T> type) {
        StringBuilder columnsAttributes = new StringBuilder();
        StringBuilder valuePosition = new StringBuilder();

        for (int i = 1; i < columns.size(); i++) {
            columnsAttributes.append(columns.get(i));
            valuePosition.append("?");
            if (i != columns.size() - 1) {
                columnsAttributes.append(",");
                valuePosition.append(",");
            }
        }

        return "insert into " + type.getSimpleName().toLowerCase(Locale.ROOT) +
                "(" + columnsAttributes + ")" + " values " +
                "(" + valuePosition + ")";
    }
}
