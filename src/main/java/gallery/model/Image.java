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

   /* @Column(name = "height")
    private Integer height;

    @Column(name = "width")
    private Integer width;*/


    private float[] features;

    /*@ManyToOne
    @JsonIgnoreProperties("")
    private Album album;//игнорируем при десереализации

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "photo_tag",
               joinColumns = @JoinColumn(name = "photos_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "tags_id", referencedColumnName = "id"))
    private Set<Tag> tags = new HashSet<>();

    public Set<Tag> getTags() {
        return tags;
    }

    public Photo tags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public Photo addTag(Tag tag) {
        this.tags.add(tag);
        tag.getPhotos().add(this);
        return this;
    }

    public Photo removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getPhotos().remove(this);
        return this;
    }*/

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

   /* public int getHeight(){
        return height;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public int getWidth(){
        return width;
    }

    public void setWidth(int width){
        this.width = width;
    }*/

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

    /*@Override
    public String toString() {
        return "Photo{" +
                "id=" + getId() +
                ", title='" + getTitle() + "'" +
                ", image='" + getImage() + "'" +
                // ", imageContentType='" + getImageContentType() + "'" +
                ", height=" + getHeight() +
                ", width=" + getWidth() +
                //   ", taken='" + getTaken() + "'" +
                //w    ", uploaded='" + getUploaded() + "'" +
                "}";
    }*/

        public String to64encode() {
            return Base64.encodeBase64String(this.image); //картинка в виде большой длинной какахи
        }
    }

