package models;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Posts extends Model {
 
    public String title;
    public Date postedAt;
    
    @Lob
    public String content;
    
    @ManyToOne
    public Users author;
    
    @OneToMany(mappedBy="post", cascade=CascadeType.ALL)
    public List<Comments> comments;
    
    public Posts(Users author, String title, String content) {
		this.comments = new ArrayList<Comments>();
        this.author = author;
        this.title = title;
        this.content = content;
        this.postedAt = new Date();
    }
    
    public Posts addComment(String author, String content){
		Comments comm = new Comments(this, author, content).save();
		this.comments.add(comm);
		this.save();
		return this;
	}
 
}
