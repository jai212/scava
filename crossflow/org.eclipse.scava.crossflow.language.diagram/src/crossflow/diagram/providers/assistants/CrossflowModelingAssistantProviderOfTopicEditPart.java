/*
 * 
 */
package crossflow.diagram.providers.assistants;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import crossflow.diagram.edit.parts.TopicEditPart;
import crossflow.diagram.edit.parts.TypeEditPart;
import crossflow.diagram.providers.CrossflowElementTypes;
import crossflow.diagram.providers.CrossflowModelingAssistantProvider;

/**
 * @generated
 */
public class CrossflowModelingAssistantProviderOfTopicEditPart extends CrossflowModelingAssistantProvider {

	/**
	* @generated
	*/
	@Override

	public List<IElementType> getRelTypesOnSource(IAdaptable source) {
		IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source.getAdapter(IGraphicalEditPart.class);
		return doGetRelTypesOnSource((TopicEditPart) sourceEditPart);
	}

	/**
	* @generated
	*/
	public List<IElementType> doGetRelTypesOnSource(TopicEditPart source) {
		List<IElementType> types = new ArrayList<IElementType>(1);
		types.add(CrossflowElementTypes.StreamType_4001);
		return types;
	}

	/**
	* @generated
	*/
	@Override

	public List<IElementType> getRelTypesOnSourceAndTarget(IAdaptable source, IAdaptable target) {
		IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source.getAdapter(IGraphicalEditPart.class);
		IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target.getAdapter(IGraphicalEditPart.class);
		return doGetRelTypesOnSourceAndTarget((TopicEditPart) sourceEditPart, targetEditPart);
	}

	/**
	* @generated
	*/
	public List<IElementType> doGetRelTypesOnSourceAndTarget(TopicEditPart source, IGraphicalEditPart targetEditPart) {
		List<IElementType> types = new LinkedList<IElementType>();
		if (targetEditPart instanceof TypeEditPart) {
			types.add(CrossflowElementTypes.StreamType_4001);
		}
		return types;
	}

	/**
	* @generated
	*/
	@Override

	public List<IElementType> getTypesForTarget(IAdaptable source, IElementType relationshipType) {
		IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source.getAdapter(IGraphicalEditPart.class);
		return doGetTypesForTarget((TopicEditPart) sourceEditPart, relationshipType);
	}

	/**
	* @generated
	*/
	public List<IElementType> doGetTypesForTarget(TopicEditPart source, IElementType relationshipType) {
		List<IElementType> types = new ArrayList<IElementType>();
		if (relationshipType == CrossflowElementTypes.StreamType_4001) {
			types.add(CrossflowElementTypes.Type_2007);
		}
		return types;
	}

	/**
	* @generated
	*/
	@Override

	public List<IElementType> getRelTypesOnTarget(IAdaptable target) {
		IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target.getAdapter(IGraphicalEditPart.class);
		return doGetRelTypesOnTarget((TopicEditPart) targetEditPart);
	}

	/**
	* @generated
	*/
	public List<IElementType> doGetRelTypesOnTarget(TopicEditPart target) {
		List<IElementType> types = new ArrayList<IElementType>(2);
		types.add(CrossflowElementTypes.TaskInput_4002);
		types.add(CrossflowElementTypes.TaskOutput_4003);
		return types;
	}

	/**
	* @generated
	*/
	@Override

	public List<IElementType> getTypesForSource(IAdaptable target, IElementType relationshipType) {
		IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target.getAdapter(IGraphicalEditPart.class);
		return doGetTypesForSource((TopicEditPart) targetEditPart, relationshipType);
	}

	/**
	* @generated
	*/
	public List<IElementType> doGetTypesForSource(TopicEditPart target, IElementType relationshipType) {
		List<IElementType> types = new ArrayList<IElementType>();
		if (relationshipType == CrossflowElementTypes.TaskInput_4002) {
			types.add(CrossflowElementTypes.Source_2003);
			types.add(CrossflowElementTypes.Sink_2004);
			types.add(CrossflowElementTypes.Task_2006);
		} else if (relationshipType == CrossflowElementTypes.TaskOutput_4003) {
			types.add(CrossflowElementTypes.Source_2003);
			types.add(CrossflowElementTypes.Sink_2004);
			types.add(CrossflowElementTypes.Task_2006);
		}
		return types;
	}

}