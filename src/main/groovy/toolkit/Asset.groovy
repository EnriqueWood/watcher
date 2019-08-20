package toolkit

class Asset<T> {
	String name
	T resource

	Asset(String name, T resource) {
		this.name = name
		this.resource = resource
	}
}
