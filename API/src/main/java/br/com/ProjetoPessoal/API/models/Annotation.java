package br.com.ProjetoPessoal.API.models;
		
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TB_ANNOTATIONS")
public class Annotation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_annotation_id")
	private Long userAnnotationId;

	@Column(name = "user_id", nullable = false, unique = false)
	private Long user_id;

	@Column(name = "title", nullable = false, unique = false)
	private String title;
	
	@Column(name = "content", nullable = true, unique = false)
	private String content;
	
	@Column(name = "registered_at")
	private LocalDateTime registeredAt;
	
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	@ManyToOne
	private User user;

	public Annotation() {
		this.setRegisteredAt(LocalDateTime.now());
		this.setUpdatedAt(LocalDateTime.now());
	}
	
	public Annotation(
		Long user_id,
		String title,
		String content
	) {
		this.setTitle(title);
		this.setUser_id(user_id);
		this.setContent(content);
		this.setRegisteredAt(LocalDateTime.now());
		this.setUpdatedAt(LocalDateTime.now());
	}	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getRegisteredAt() {
		return registeredAt;
	}

	public void setRegisteredAt(LocalDateTime registeredAt) {
		this.registeredAt = registeredAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
		this.setUser_id(this.user.getId());
		this.setUserAnnotationId(this.user.getAnnotations());
	}

	public Long getUserAnnotationId() {
		return this.userAnnotationId;
	}

	public void setUserAnnotationId(List<Annotation> annotations) {	
		final Long userLastAnnotationId = this.getUser().getLastAnnotationId();

		final Annotation lastAnnotationRegistered = annotations.size() - 1 > 0
			? annotations.get(annotations.size() - 1)
			: null;
			
		final Long lastAnnotationId = lastAnnotationRegistered == null
			? 0L
			: lastAnnotationRegistered.getUserAnnotationId();

		this.userAnnotationId = lastAnnotationId > userLastAnnotationId
			? lastAnnotationId : userLastAnnotationId;

		this.userAnnotationId++;

		this.getUser().setLastAnnotationId(this.userAnnotationId);
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
