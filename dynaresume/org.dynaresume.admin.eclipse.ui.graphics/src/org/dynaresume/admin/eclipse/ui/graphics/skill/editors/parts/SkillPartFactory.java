package org.dynaresume.admin.eclipse.ui.graphics.skill.editors.parts;

import org.dynaresume.admin.eclipse.ui.graphics.skill.editors.model.Connection;
import org.dynaresume.admin.eclipse.ui.graphics.skill.editors.model.GraphicalSkill;
import org.dynaresume.admin.eclipse.ui.graphics.skill.editors.model.SkillsDiagram;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

public class SkillPartFactory implements EditPartFactory {

	public static final EditPartFactory INSTANCE = new SkillPartFactory();

	public EditPart createEditPart(EditPart context, Object modelElement) {
		// get EditPart for model element
		EditPart part = getPartForElement(modelElement);
		// store model element in EditPart
		part.setModel(modelElement);
		return part;
	}

	/**
	 * Maps an object to an EditPart.
	 * 
	 * @throws RuntimeException
	 *             if no match was found (programming error)
	 */
	private EditPart getPartForElement(Object modelElement) {
		if (modelElement instanceof SkillsDiagram) {
			return new SkillsDiagramPart();
		}
		if (modelElement instanceof GraphicalSkill) {
			return new SkillPart();
		}
	    if (modelElement instanceof Connection) { 
	        return new ConnectionPart(); 
	      } 
		throw new RuntimeException("Can't create part for model element: "
				+ ((modelElement != null) ? modelElement.getClass().getName()
						: "null"));
	}

}