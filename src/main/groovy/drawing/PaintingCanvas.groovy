package drawing

/* Canvas containing a group layers which contains drawable elements */

interface PaintingCanvas {
	void addLayer(ILayer layer)

	// Paints the content of every drawable in its respective layer
	void paintLayers()

	//request a petition to be displayed
	void show()

	/* Recalculate attributes in drawable elements
	 	returns updated layers, if force is set true all layers get updated
	 */

	List<ILayer> updateLayers(boolean force)

	void addLayers(List<ILayer> layers)
}
