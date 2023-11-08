package View.data;

public class NewsData {
    private String title, link;
    // Để constructor là public thì mới có khả năng truy cập ngoài package được nhé.
    public NewsData(){};

    public NewsData(String title, String link){
        this.title = title;
        this.link = link;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setLink(String link){
        this.link = link;
    }

    public String getTitle(){
        return title;
    }

    public String getLink(){
        return link;
    }
}
