package OntoUML.diagram.edit.policies;

import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.SemanticEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IEditHelperContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientReferenceRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class OntoUMLBaseItemSemanticEditPolicy extends SemanticEditPolicy {

	/**
	 * Extended request data key to hold editpart visual id.
	 * 
	 * @generated
	 */
	public static final String VISUAL_ID_KEY = "visual_id"; //$NON-NLS-1$

	/**
	 * Extended request data key to hold editpart visual id.
	 * Add visual id of edited editpart to extended data of the request
	 * so command switch can decide what kind of diagram element is being edited.
	 * It is done in those cases when it's not possible to deduce diagram
	 * element kind from domain element.
	 * 
	 * @generated
	 */
	public Command getCommand(Request request) {
		if (request instanceof ReconnectRequest) {
			Object view = ((ReconnectRequest) request).getConnectionEditPart()
					.getModel();
			if (view instanceof View) {
				Integer id = new Integer(
						OntoUML.diagram.part.OntoUMLVisualIDRegistry
								.getVisualID((View) view));
				request.getExtendedData().put(VISUAL_ID_KEY, id);
			}
		}
		return super.getCommand(request);
	}

	/**
	 * Returns visual id from request parameters.
	 * 
	 * @generated
	 */
	protected int getVisualID(IEditCommandRequest request) {
		Object id = request.getParameter(VISUAL_ID_KEY);
		return id instanceof Integer ? ((Integer) id).intValue() : -1;
	}

	/**
	 * @generated
	 */
	protected Command getSemanticCommand(IEditCommandRequest request) {
		IEditCommandRequest completedRequest = completeRequest(request);
		Object editHelperContext = completedRequest.getEditHelperContext();
		if (editHelperContext instanceof View
				|| (editHelperContext instanceof IEditHelperContext && ((IEditHelperContext) editHelperContext)
						.getEObject() instanceof View)) {
			// no semantic commands are provided for pure design elements
			return null;
		}
		if (editHelperContext == null) {
			editHelperContext = ViewUtil
					.resolveSemanticElement((View) getHost().getModel());
		}
		IElementType elementType = ElementTypeRegistry.getInstance()
				.getElementType(editHelperContext);
		if (elementType == ElementTypeRegistry.getInstance().getType(
				"org.eclipse.gmf.runtime.emf.type.core.default")) { //$NON-NLS-1$ 
			elementType = null;
		}
		Command semanticCommand = getSemanticCommandSwitch(completedRequest);
		if (elementType != null) {
			if (semanticCommand != null) {
				ICommand command = semanticCommand instanceof ICommandProxy ? ((ICommandProxy) semanticCommand)
						.getICommand()
						: new CommandProxy(semanticCommand);
				completedRequest
						.setParameter(
								OntoUML.diagram.edit.helpers.OntoUMLBaseEditHelper.EDIT_POLICY_COMMAND,
								command);
			}
			ICommand command = elementType.getEditCommand(completedRequest);
			if (command != null) {
				if (!(command instanceof CompositeTransactionalCommand)) {
					TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
							.getEditingDomain();
					command = new CompositeTransactionalCommand(editingDomain,
							command.getLabel()).compose(command);
				}
				semanticCommand = new ICommandProxy(command);
			}
		}
		boolean shouldProceed = true;
		if (completedRequest instanceof DestroyRequest) {
			shouldProceed = shouldProceed((DestroyRequest) completedRequest);
		}
		if (shouldProceed) {
			if (completedRequest instanceof DestroyRequest) {
				TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
						.getEditingDomain();
				Command deleteViewCommand = new ICommandProxy(
						new DeleteCommand(editingDomain, (View) getHost()
								.getModel()));
				semanticCommand = semanticCommand == null ? deleteViewCommand
						: semanticCommand.chain(deleteViewCommand);
			}
			return semanticCommand;
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getSemanticCommandSwitch(IEditCommandRequest req) {
		if (req instanceof CreateRelationshipRequest) {
			return getCreateRelationshipCommand((CreateRelationshipRequest) req);
		} else if (req instanceof CreateElementRequest) {
			return getCreateCommand((CreateElementRequest) req);
		} else if (req instanceof ConfigureRequest) {
			return getConfigureCommand((ConfigureRequest) req);
		} else if (req instanceof DestroyElementRequest) {
			return getDestroyElementCommand((DestroyElementRequest) req);
		} else if (req instanceof DestroyReferenceRequest) {
			return getDestroyReferenceCommand((DestroyReferenceRequest) req);
		} else if (req instanceof DuplicateElementsRequest) {
			return getDuplicateCommand((DuplicateElementsRequest) req);
		} else if (req instanceof GetEditContextRequest) {
			return getEditContextCommand((GetEditContextRequest) req);
		} else if (req instanceof MoveRequest) {
			return getMoveCommand((MoveRequest) req);
		} else if (req instanceof ReorientReferenceRelationshipRequest) {
			return getReorientReferenceRelationshipCommand((ReorientReferenceRelationshipRequest) req);
		} else if (req instanceof ReorientRelationshipRequest) {
			return getReorientRelationshipCommand((ReorientRelationshipRequest) req);
		} else if (req instanceof SetRequest) {
			return getSetCommand((SetRequest) req);
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getConfigureCommand(ConfigureRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getSetCommand(SetRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getEditContextCommand(GetEditContextRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getDestroyReferenceCommand(DestroyReferenceRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getDuplicateCommand(DuplicateElementsRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getMoveCommand(MoveRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getReorientReferenceRelationshipCommand(
			ReorientReferenceRelationshipRequest req) {
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * @generated
	 */
	protected Command getReorientRelationshipCommand(
			ReorientRelationshipRequest req) {
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * @generated
	 */
	protected final Command getGEFWrapper(ICommand cmd) {
		return new ICommandProxy(cmd);
	}

	/**
	 * @deprecated use getGEFWrapper() instead
	 * @generated
	 */
	protected final Command getMSLWrapper(ICommand cmd) {
		// XXX deprecated: use getGEFWrapper() instead
		return getGEFWrapper(cmd);
	}

	/**
	 * @generated
	 */
	protected EObject getSemanticElement() {
		return ViewUtil.resolveSemanticElement((View) getHost().getModel());
	}

	/**
	 * Returns editing domain from the host edit part.
	 * 
	 * @generated
	 */
	protected TransactionalEditingDomain getEditingDomain() {
		return ((IGraphicalEditPart) getHost()).getEditingDomain();
	}

	/**
	 * Creates command to destroy the link.
	 * 
	 * @generated
	 */
	protected Command getDestroyElementCommand(View view) {
		EditPart editPart = (EditPart) getHost().getViewer()
				.getEditPartRegistry().get(view);
		DestroyElementRequest request = new DestroyElementRequest(
				getEditingDomain(), false);
		return editPart.getCommand(new EditCommandRequestWrapper(request,
				Collections.EMPTY_MAP));
	}

	/**
	 * Creates commands to destroy all host incoming and outgoing links.
	 * 
	 * @generated
	 */
	protected CompoundCommand getDestroyEdgesCommand() {
		CompoundCommand cmd = new CompoundCommand();
		View view = (View) getHost().getModel();
		for (Iterator it = view.getSourceEdges().iterator(); it.hasNext();) {
			cmd.add(getDestroyElementCommand((Edge) it.next()));
		}
		for (Iterator it = view.getTargetEdges().iterator(); it.hasNext();) {
			cmd.add(getDestroyElementCommand((Edge) it.next()));
		}
		return cmd;
	}

	/**
	 * @generated
	 */
	protected void addDestroyShortcutsCommand(CompoundCommand command) {
		View view = (View) getHost().getModel();
		if (view.getEAnnotation("Shortcut") != null) { //$NON-NLS-1$
			return;
		}
		for (Iterator it = view.getDiagram().getChildren().iterator(); it
				.hasNext();) {
			View nextView = (View) it.next();
			if (nextView.getEAnnotation("Shortcut") == null || !nextView.isSetElement() || nextView.getElement() != view.getElement()) { //$NON-NLS-1$
				continue;
			}
			command.add(getDestroyElementCommand(nextView));
		}
	}

	/**
	 * @generated
	 */
	public static class LinkConstraints {

		/**
		 * @generated
		 */
		private static final String OPPOSITE_END_VAR = "oppositeEnd"; //$NON-NLS-1$

		/**
		 * @generated
		 */
		public static boolean canCreateCharacterization_4001(
				OntoUML.Container container, OntoUML.Element source,
				OntoUML.Element target) {
			return canExistCharacterization_4001(container, source, target);
		}

		/**
		 * @generated
		 */
		public static boolean canCreateComponentOf_4002(
				OntoUML.Container container, OntoUML.Element source,
				OntoUML.Element target) {
			return canExistComponentOf_4002(container, source, target);
		}

		/**
		 * @generated
		 */
		public static boolean canCreateDatatypeAssociation_4003(
				OntoUML.Container container, OntoUML.Type source,
				OntoUML.Type target) {
			return canExistDatatypeAssociation_4003(container, source, target);
		}

		/**
		 * @generated
		 */
		public static boolean canCreateDerivation_4004(
				OntoUML.Container container, OntoUML.Element source,
				OntoUML.Element target) {
			return canExistDerivation_4004(container, source, target);
		}

		/**
		 * @generated
		 */
		public static boolean canCreateFormalAssociation_4005(
				OntoUML.Container container, OntoUML.Type source,
				OntoUML.Type target) {
			return canExistFormalAssociation_4005(container, source, target);
		}

		/**
		 * @generated
		 */
		public static boolean canCreateGeneralization_4006(
				OntoUML.Container container, OntoUML.Element source,
				OntoUML.Element target) {
			return canExistGeneralization_4006(container, source, target);
		}

		/**
		 * @generated
		 */
		public static boolean canCreateMaterialAssociation_4007(
				OntoUML.Container container, OntoUML.Type source,
				OntoUML.Type target) {
			return canExistMaterialAssociation_4007(container, source, target);
		}

		/**
		 * @generated
		 */
		public static boolean canCreateMediation_4008(
				OntoUML.Container container, OntoUML.Element source,
				OntoUML.Element target) {
			return canExistMediation_4008(container, source, target);
		}

		/**
		 * @generated
		 */
		public static boolean canCreateMemberOf_4009(
				OntoUML.Container container, OntoUML.Element source,
				OntoUML.Element target) {
			return canExistMemberOf_4009(container, source, target);
		}

		/**
		 * @generated
		 */
		public static boolean canCreateSubCollectionOf_4010(
				OntoUML.Container container, OntoUML.Element source,
				OntoUML.Element target) {
			return canExistSubCollectionOf_4010(container, source, target);
		}

		/**
		 * @generated
		 */
		public static boolean canCreateSubQuantityOf_4011(
				OntoUML.Container container, OntoUML.Element source,
				OntoUML.Element target) {
			return canExistSubQuantityOf_4011(container, source, target);
		}

		/**
		 * @generated
		 */
		public static boolean canCreateProperty_4012(
				OntoUML.Container container, OntoUML.Property source,
				OntoUML.DirectedBinaryRelationship target) {
			return canExistProperty_4012(container, source, target);
		}

		/**
		 * @generated
		 */
		public static boolean canCreateBinaryAssociationAtom_4013(
				OntoUML.Container container, OntoUML.URML.Rule source,
				OntoUML.Association target) {
			return canExistBinaryAssociationAtom_4013(container, source, target);
		}

		/**
		 * @generated
		 */
		public static boolean canCreateBinaryAssociationAtom_4014(
				OntoUML.Container container, OntoUML.URML.Rule source,
				OntoUML.Association target) {
			return canExistBinaryAssociationAtom_4014(container, source, target);
		}

		/**
		 * @generated
		 */
		public static boolean canCreateBinaryAssociationAtom_4015(
				OntoUML.Container container,
				OntoUML.URML.DerivationRule source, OntoUML.Association target) {
			return canExistBinaryAssociationAtom_4015(container, source, target);
		}

		/**
		 * @generated
		 */
		public static boolean canCreateClassifierAtom_4016(
				OntoUML.Container container, OntoUML.URML.Rule source,
				OntoUML.Class target) {
			return canExistClassifierAtom_4016(container, source, target);
		}

		/**
		 * @generated
		 */
		public static boolean canCreateClassifierAtom_4017(
				OntoUML.Container container, OntoUML.URML.Rule source,
				OntoUML.Class target) {
			return canExistClassifierAtom_4017(container, source, target);
		}

		/**
		 * @generated
		 */
		public static boolean canCreateClassifierAtom_4018(
				OntoUML.Container container,
				OntoUML.URML.DerivationRule source, OntoUML.Class target) {
			return canExistClassifierAtom_4018(container, source, target);
		}

		/**
		 * @generated
		 */
		public static boolean canExistCharacterization_4001(
				OntoUML.Container container, OntoUML.Element source,
				OntoUML.Element target) {

			return true;
		}

		/**
		 * @generated
		 */
		public static boolean canExistComponentOf_4002(
				OntoUML.Container container, OntoUML.Element source,
				OntoUML.Element target) {

			return true;
		}

		/**
		 * @generated
		 */
		public static boolean canExistDatatypeAssociation_4003(
				OntoUML.Container container, OntoUML.Type source,
				OntoUML.Type target) {

			return true;
		}

		/**
		 * @generated
		 */
		public static boolean canExistDerivation_4004(
				OntoUML.Container container, OntoUML.Element source,
				OntoUML.Element target) {

			return true;
		}

		/**
		 * @generated
		 */
		public static boolean canExistFormalAssociation_4005(
				OntoUML.Container container, OntoUML.Type source,
				OntoUML.Type target) {

			return true;
		}

		/**
		 * @generated
		 */
		public static boolean canExistGeneralization_4006(
				OntoUML.Container container, OntoUML.Element source,
				OntoUML.Element target) {

			return true;
		}

		/**
		 * @generated
		 */
		public static boolean canExistMaterialAssociation_4007(
				OntoUML.Container container, OntoUML.Type source,
				OntoUML.Type target) {

			return true;
		}

		/**
		 * @generated
		 */
		public static boolean canExistMediation_4008(
				OntoUML.Container container, OntoUML.Element source,
				OntoUML.Element target) {

			return true;
		}

		/**
		 * @generated
		 */
		public static boolean canExistMemberOf_4009(
				OntoUML.Container container, OntoUML.Element source,
				OntoUML.Element target) {

			return true;
		}

		/**
		 * @generated
		 */
		public static boolean canExistSubCollectionOf_4010(
				OntoUML.Container container, OntoUML.Element source,
				OntoUML.Element target) {

			return true;
		}

		/**
		 * @generated
		 */
		public static boolean canExistSubQuantityOf_4011(
				OntoUML.Container container, OntoUML.Element source,
				OntoUML.Element target) {

			return true;
		}

		/**
		 * @generated
		 */
		public static boolean canExistProperty_4012(
				OntoUML.Container container, OntoUML.Property source,
				OntoUML.DirectedBinaryRelationship target) {

			return true;
		}

		/**
		 * @generated
		 */
		public static boolean canExistBinaryAssociationAtom_4013(
				OntoUML.Container container, OntoUML.URML.Rule source,
				OntoUML.Association target) {

			return true;
		}

		/**
		 * @generated
		 */
		public static boolean canExistBinaryAssociationAtom_4014(
				OntoUML.Container container, OntoUML.URML.Rule source,
				OntoUML.Association target) {

			return true;
		}

		/**
		 * @generated
		 */
		public static boolean canExistBinaryAssociationAtom_4015(
				OntoUML.Container container,
				OntoUML.URML.DerivationRule source, OntoUML.Association target) {

			return true;
		}

		/**
		 * @generated
		 */
		public static boolean canExistClassifierAtom_4016(
				OntoUML.Container container, OntoUML.URML.Rule source,
				OntoUML.Class target) {

			return true;
		}

		/**
		 * @generated
		 */
		public static boolean canExistClassifierAtom_4017(
				OntoUML.Container container, OntoUML.URML.Rule source,
				OntoUML.Class target) {

			return true;
		}

		/**
		 * @generated
		 */
		public static boolean canExistClassifierAtom_4018(
				OntoUML.Container container,
				OntoUML.URML.DerivationRule source, OntoUML.Class target) {

			return true;
		}
	}

}
