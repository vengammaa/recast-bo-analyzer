package com.recast.recast.bo.analyzer.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="connection_path")
public class ConnectionPath implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pathId;
	
	private Integer connectionId;

	private String pathName;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pathId", orphanRemoval = true)
	private List<ReportPath> reportPathList;
	
	public Integer getPathId() {
		return pathId;
	}
	public void setPathId(Integer pathId) {
		this.pathId = pathId;
	}
	public Integer getConnectionId() {
		return connectionId;
	}
	public void setConnectionId(Integer connectionId) {
		this.connectionId = connectionId;
	}
	public String getPathName() {
		return pathName;
	}
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}
	@Override
	public String toString() {
		return "ConnectionPath [pathId=" + pathId + ", connectionId=" + connectionId + ", pathName=" + pathName + "]";
	}
	
	

}
