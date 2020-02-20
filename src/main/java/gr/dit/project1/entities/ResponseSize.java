package gr.dit.project1.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "response_size")
public class ResponseSize {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long size;

	@OneToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "request_id", referencedColumnName = "id")
	private Request requestId;

	public ResponseSize() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Request getRequestId() {
		return requestId;
	}

	public void setRequestId(Request requestId) {
		this.requestId = requestId;
	}

}
