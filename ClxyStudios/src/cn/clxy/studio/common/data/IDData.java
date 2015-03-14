package cn.clxy.studio.common.data;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import cn.clxy.studio.common.util.BeanUtil;

@MappedSuperclass
public abstract class IDData implements Serializable {

	@Id
	@GeneratedValue
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return BeanUtil.hash(id);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		return Objects.equals(((IDData) obj).getId(), id);
	}

	private static final long serialVersionUID = 1L;
}
