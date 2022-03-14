package business;

import java.io.Serializable;

public class CompositeProduct implements Serializable {
    String name;
    String composite;

    public CompositeProduct(String name, String composite) {
        super();
        this.name = name;
        this.composite = composite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComposite() {
        return composite;
    }

    public void setComposite(String composite) {
        this.composite = composite;
    }

    @Override
    public String toString(){
        return name + " " + composite;
    }
}
