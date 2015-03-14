package cn.clxy.studio.home.data;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.clxy.studio.common.data.BaseData;

@Entity
@Table(name = "reputation")
public class ReputationData extends BaseData {

	private String site;
	private String reputation;
	private String gold;
	private String silver;
	private String bronze;
	private Date update = new Date();

	public ReputationData() {
	}

	public boolean isSame(ReputationData other) {

		if (other == null) {
			return false;
		}

		return Objects.equals(reputation, other.getReputation())
				&& Objects.equals(gold, other.getGold())
				&& Objects.equals(silver, other.getSilver())
				&& Objects.equals(bronze, other.getBronze());
	}

	public ReputationData(String site) {
		this.site = site;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Date getUpdate() {
		return update;
	}

	public void setUpdate(Date update) {
		this.update = update;
	}

	public String getReputation() {
		return reputation;
	}

	public void setReputation(String reputation) {
		this.reputation = reputation;
	}

	public String getGold() {
		return gold;
	}

	public void setGold(String gold) {
		this.gold = gold;
	}

	public String getSilver() {
		return silver;
	}

	public void setSilver(String silver) {
		this.silver = silver;
	}

	public String getBronze() {
		return bronze;
	}

	public void setBronze(String bronze) {
		this.bronze = bronze;
	}

	private static final long serialVersionUID = 1L;
}
