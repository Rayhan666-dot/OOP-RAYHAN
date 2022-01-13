public class Kampus {
    private int id;
    private String jurusan = null;
    private String nama = null;
    public Object conn;

    public Kampus(int inputId, String inputJurusan, String inputNama ) {
        this.id = inputId;
        this.jurusan = inputJurusan ;
        this.nama = inputNama;
       
    }


    public int getId(){
        return id;
    }

    public String getJurusan(){
        return jurusan;
    }

    public String getNama(){
        return nama;
    }


    public void setId(String text) {
    }

    public void setJurusan() {
    }

    public void setNama(String text) {
    }

}
