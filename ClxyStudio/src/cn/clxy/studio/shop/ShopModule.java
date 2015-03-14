package cn.clxy.studio.shop;

import java.util.Arrays;
import java.util.List;

import cn.clxy.studio.common.Module;
import cn.clxy.studio.shop.model.Product;

public class ShopModule implements Module {
	/**
	 * product list ***Just for test
	 */
	public static List<Product> products = Arrays.asList(new Product("Gouda",
			"Gouda is a yellowish Dutch[...]", 1.65), new Product("Edam",
			"Edam (Dutch Edammer) is a D[...]", 1.05), new Product("Maasdam",
			"Maasdam cheese is a Dutc[...]", 2.35), new Product("Brie",
			"Brie is a soft cows' milk c[...]", 3.15), new Product(
			"Buxton Blue", "Buxton Blue cheese i[...]", 0.99), new Product(
			"Parmesan", "Parmesan is a grana, a [...]", 1.99), new Product(
			"Cheddar", "Cheddar cheese is a hard[...]", 2.95), new Product(
			"Roquefort", "Roquefort is a ewe's-m[...]", 1.67), new Product(
			"Boursin", "Boursin Cheese is a soft[...]", 1.33), new Product(
			"Camembert", "Camembert is a soft, c[...]", 1.69), new Product(
			"Emmental", "Emmental is a yellow, m[...]", 2.39), new Product(
			"Reblochon", "Reblochon is a French [...]", 2.99));

	@Override
	public void load() {
		// Application.get().mountPackage("shop", ShopHomePage.class);
	}

	@Override
	public String getHomePage() {
		return null;
	}

	@Override
	public String getBriefPage() {
		return null;
	}

	@Override
	public String getMenuItem() {
		return ("shopMenu");
	}
}
