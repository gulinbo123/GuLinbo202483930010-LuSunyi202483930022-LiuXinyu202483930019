package com.nuist.test.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "player")
public class PlayerTable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pid")
	private Integer pid;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@ManyToMany
	@JoinTable(
			name = "player_world",
			joinColumns = @JoinColumn(name = "pid"),
			inverseJoinColumns = @JoinColumn(name = "wid")
	)
	private Set<WorldTable> worlds = new HashSet<>();

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<WorldTable> getWorlds() {
		return worlds;
	}

	public void setWorlds(Set<WorldTable> worlds) {
		this.worlds = worlds;
	}
}
