package toolkit

import java.awt.Font
import java.awt.image.BufferedImage

class ResourceBox {
	Map<String, Asset> assets

	ResourceBox() {
		this.assets = [:]
	}

	void addResource(Asset resource) {
		assets[resource.name] = resource
	}

	void addAllAssets(List<Asset> assets) {
		assets.each { addResource(it) }
	}

	Asset getAsset(String name) {
		assets[name]
	}

	BufferedImage getImage(String name) {
		assets[name].resource as BufferedImage
	}

	Font getFont(String name, float size = 12f) {
		(assets[name].resource as Font).deriveFont(size)
	}
}
