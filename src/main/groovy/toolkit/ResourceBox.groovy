package toolkit

import drawing.IAsset

import java.awt.Font
import java.awt.image.BufferedImage

class ResourceBox {
	Map<String, IAsset> assets

	ResourceBox() {
		this.assets = [:]
	}

	void addResource(IAsset resource) {
		assets[resource.name] = resource
	}

	void addAllAssets(List<IAsset> assets) {
		assets.each { addResource(it) }
	}

	IAsset getAsset(String name) {
		assets[name]
	}

	BufferedImage getImage(String name) {
		assets[name].resource as BufferedImage
	}

	Font getFont(String name, float size = 12f) {
		(assets[name].resource as Font).deriveFont(size)
	}

	List<IAsset> getAssetList() {
		new ArrayList<IAsset>(assets.values())
	}
}
