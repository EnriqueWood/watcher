package drawing

/* Canvas containing a group layers which contains drawable elements */

interface IPaintingCanvas {
	void addScreen(IScreen screen)

	// Paints the content of every drawable in its respective layer
	void paint()

	//request a petition to be displayed
	void show()

	/* Recalculate attributes in drawable elements
	 	returns updated layers, if force is set true all layers get updated
	 */

	List<IScreen> updateScreens(boolean force)

	void addScreens(List<IScreen> screens)
}
