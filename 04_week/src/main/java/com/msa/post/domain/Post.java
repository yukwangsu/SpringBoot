package com.msa.post.domain;

import com.msa.comment.domain.Comment;
import com.msa.post.dto.PostDto;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;

//@Entity 어노테이션은 JPA를 사용해 테이블과 매핑할 클래스에 붙여주는 어노테이션이다.
//이 어노테이션을 붙임으로써 JPA가 해당 클래스를 관리하게 된다.
@Entity
@Getter
//해당 클래스에 Auditing 기능을 포함
@EntityListeners(AuditingEntityListener.class)
public class Post {

	@Id
	//기본 키 생성을 데이터베이스에 위임
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	private long id;

	@Column(name="title")
	private String title;
	
	@Column(name="content")
	private String content;
	
	@CreatedDate
	private LocalDateTime createdAt = LocalDateTime.now();

	@LastModifiedDate
	private LocalDateTime updatedAt = LocalDateTime.now();

	//@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)는
	// Java 언어와 Java Persistence API (JPA)를 사용하는 경우 엔티티 클래스에서 관계 매핑을 정의하는 어노테이션입니다.
	// 이 어노테이션은 주로 ORM(Object-Relational Mapping)을 사용하여 데이터베이스와 상호 작용하는데 사용됩니다.
	//
	//여기에서 @OneToMany는 엔티티 클래스의 필드에 사용되며, 관계 매핑을 정의합니다.
	// 이 어노테이션은 일대다(1:N) 관계를 나타냅니다.
	// 즉, 하나의 엔티티가 여러 다른 엔티티와 관련되어 있음을 의미합니다.
	//
	//그리고 cascade = CascadeType.ALL은 부모 엔티티가 변경되거나 삭제될 때
	// 관련된 자식 엔티티도 함께 변경 또는 삭제됨을 나타냅니다.
	// CASCADE는 부모-자식 간의 작업 전파 규칙을 정의하는데 사용됩니다.
	//
	//마지막으로, orphanRemoval = true는 자식 엔티티가 부모 엔티티와 연결이 끊어진 경우 자동으로 삭제되어야 함을 나타냅니다.
	// 이것은 부모-자식 관계에서 유효하지 않은 자식을 자동으로 삭제하는데 사용됩니다.
	//
	//예를 들어, 만약 이 관계가 부모 엔티티와 자식 엔티티 간의 일대다 관계를 정의한다면,
	// 위의 어노테이션을 사용하면 부모 엔티티를 삭제할 때 관련된 모든 자식 엔티티가 삭제되며,
	// 관계가 끊어진 자식 엔티티가 자동으로 삭제됩니다.
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)

	//여기에서 @JoinColumn은 외래 키(column)를 지정할 때 사용됩니다.
	// name 속성은 외래 키의 이름을 지정하는 것이며,
	// 이 경우 "id"라는 외래 키를 가지도록 정의한 것입니다.
	@JoinColumn(name = "id")
	private Set<Comment> comments;
	
	public Post() {
		super();
	}

	public Post(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public Post(String title, String content, Set<Comment> comments) {
		this.title = title;
		this.content = content;
		this.comments = comments;
	}

	public PostDto convert2DTO() {
		return new PostDto(this.getTitle(), this.getContent());
	}
}
