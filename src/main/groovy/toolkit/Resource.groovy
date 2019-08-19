package toolkit

class Resource<T> {
	String name
	T resource

	Resource(String name, T resource) {
		this.name = name
		this.resource = resource
	}
}
