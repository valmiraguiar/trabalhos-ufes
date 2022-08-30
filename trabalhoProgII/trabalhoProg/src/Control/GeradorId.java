package Control;

public abstract class GeradorId {

    private static int id = 0;

    public static int gerarId() {
        return ++id;
    }

    public static int getId() {
        return id;
    }
}
