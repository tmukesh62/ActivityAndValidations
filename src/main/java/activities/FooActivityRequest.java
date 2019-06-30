package activities;

import java.util.List;

public class FooActivityRequest {
    private Integer num;
    private String text;
    private List<Double> items;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Double> getItems() {
        return items;
    }

    public void setItems(List<Double> items) {
        this.items = items;
    }
}
