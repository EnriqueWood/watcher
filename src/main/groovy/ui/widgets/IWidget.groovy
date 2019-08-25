package ui.widgets

import drawing.IDrawable
import drawing.IUpdatable
import state.IStateManaged

interface IWidget extends IDrawable, IStateManaged, IUpdatable {
}
