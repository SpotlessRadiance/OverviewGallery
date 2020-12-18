package gallery.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "images")
public class Image
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    @Column(name = "image", nullable = false)
    private byte[] image;

    @ManyToOne()
    @JoinColumn(nullable = false, name = "authorId")
    private User author;

    public User getAuthor() {
        return this.author;
    }

    public void setAuthor(User author){
        this.author = author;
    }


    private float[] features;

    public Image(){}

    public Image(String title, byte[] image){
        this.title = title;
        this.image = image;
    }

    public Image(Long id, String title, byte[] image){
        this.title = title;
        this.image = image;
        this.id = id;
    }

    public Long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setFeatures(float[] features){
        this.features = features;
    }

    public float[] getFeatures(){
        return features;
    }


    @Override
    public String toString() {
        return "Image{" + "  title='" + title + '\'' + ", blob='" + image + '\'' + '}';
    }

        public String to64encode() {
            return Base64.encodeBase64String(this.image); 
        }
    }

