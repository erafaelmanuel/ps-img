package io.ermdev.papershelf.psimg;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_image")
public class Image {

    @Id
    @Column(name = "_id")
    private String id = "";

    @Column(name = "_src")
    private String src = "";

    public Image() {}

    public Image(String id, String src) {
        this.id = id;
        this.src = src;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
