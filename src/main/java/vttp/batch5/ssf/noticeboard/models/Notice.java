package vttp.batch5.ssf.noticeboard.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Notice {
    @NotEmpty(message="Title is mandatory")
    @Size(min =3, max =128, message = "Length between 3 and 128 characters")
    private String title;
    @Email(message = "Email format is wrong")
    @NotBlank(message = "Email is mandatory")
    private String poster;
    @NotNull(message="Post date is mandatory")
    @Future(message = "Post date must be in the future")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date postDate;
    @NotEmpty(message="Categories is mandatory")
    private List<String> categories;
    @NotEmpty(message="Text is mandatory")
    private String text;
    
    public Notice(
            @NotEmpty(message = "Title is mandatory") @Size(min = 3, max = 128, message = "Length between 3 and 128 characters") String title,
            @Email(message = "Email format is wrong") @NotBlank(message = "Email is mandatory") String poster,
            @NotNull(message = "Post date is mandatory") @Future(message = "Post date must be in the future") Date postDate,
            @NotEmpty(message = "Categories is mandatory") List<String> categories,
            @NotEmpty(message = "Text is mandatory") String text) {
        this.title = title;
        this.poster = poster;
        this.postDate = postDate;
        this.categories = categories;
        this.text = text;
    }

    public Notice() {
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPoster() {
        return poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }
    public Date getPostDate() {
        return postDate;
    }
    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
    
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

     public String simpleDueDate() {
        if (postDate != null) {
            long epochMilliseconds = postDate.getTime();
            Date retrievedDate = new Date(epochMilliseconds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(retrievedDate);
        }
        return "";
    }
    
}
