package models;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;

@Entity
public class Comments extends Model{
		
		public String author;
		public Date postedAt;
		
		@ManyToOne
		public Posts post;
		
		@Lob
		public String content;
		
		public Comments(Posts post, String author, String content) {
			this.post = post;
			this.author = author;
			this.content = content;
			this.postedAt = new Date();
		}
}
