/*
* 
*/
package crossflow.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

import crossflow.diagram.edit.commands.Field2CreateCommand;
import crossflow.diagram.providers.CrossflowElementTypes;

/**
 * @generated
 */
public class ScriptedTaskScriptedTaskOutputVariablesCompartmentItemSemanticEditPolicy
		extends CrossflowBaseItemSemanticEditPolicy {

	/**
	* @generated
	*/
	public ScriptedTaskScriptedTaskOutputVariablesCompartmentItemSemanticEditPolicy() {
		super(CrossflowElementTypes.ScriptedTask_2015);
	}

	/**
	* @generated
	*/
	protected Command getCreateCommand(CreateElementRequest req) {
		if (CrossflowElementTypes.Field_3003 == req.getElementType()) {
			return getGEFWrapper(new Field2CreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

}
