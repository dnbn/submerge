package submerge.web.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "user_authorities", schema = "public")
public class UserAuthorities implements Serializable {

	private static final long serialVersionUID = 7751554145509556662L;
	private UserAuthoritiesId id;
	private User user;
	private Authorities authorities;
	private Date lastUpdate;

	public UserAuthorities() {
	}

	public UserAuthorities(UserAuthoritiesId id, User user, Authorities authorities, Date lastUpdate) {
		this.id = id;
		this.user = user;
		this.authorities = authorities;
		this.lastUpdate = lastUpdate;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "fkIdAccount", column = @Column(name = "fk_id_account", nullable = false)),
			@AttributeOverride(name = "fkIdAuthorities", column = @Column(name = "fk_id_authorities", nullable = false)) })
	public UserAuthoritiesId getId() {
		return this.id;
	}

	public void setId(UserAuthoritiesId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_id_account", nullable = false, insertable = false, updatable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User account) {
		this.user = account;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_authorities", nullable = false, insertable = false, updatable = false)
	public Authorities getAuthorities() {
		return this.authorities;
	}

	public void setAuthorities(Authorities authorities) {
		this.authorities = authorities;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update", nullable = false, length = 13)
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("User : ").append(this.user.getName());
		sb.append("\nLast update : ").append(this.lastUpdate);
		sb.append("\nId : ").append(this.id);
		return sb.toString();
	}
}
