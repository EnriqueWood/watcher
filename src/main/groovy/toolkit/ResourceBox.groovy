package toolkit

import java.awt.Font
import java.awt.image.BufferedImage

class ResourceBox {
	Map<String, Resource> resources

	ResourceBox() {
		this.resources = [:]
	}

	void addResource(Resource resource) {
		resources[resource.name] = resource
	}

	void addAllResources(List<Resource> resources) {
		resources.each { addResource(it) }
	}

	Resource getResource(String name) {
		resources[name]
	}

	BufferedImage getImage(String name) {
		resources[name].resource as BufferedImage
	}

	Font getFont(String name, float size = 12f) {
		(resources[name].resource as Font).deriveFont(size)
	}
}
