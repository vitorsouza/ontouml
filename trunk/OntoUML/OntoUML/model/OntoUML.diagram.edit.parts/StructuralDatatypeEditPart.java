package OntoUML.diagram.edit.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ConstrainedToolbarLayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;

import OntoUML.DatatypeAssociation;
import OntoUML.Property;
import OntoUML.StructuralDatatype;

/**
 * @generated
 */
public class StructuralDatatypeEditPart extends ShapeNodeEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 1012;

	/**
	 * @generated
	 */
	protected IFigure contentPane;

	/**
	 * @generated
	 */
	protected IFigure primaryShape;

	/**
	 * @generated
	 */
	public StructuralDatatypeEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		installEditPolicy(EditPolicyRoles.CREATION_ROLE,
				new CreationEditPolicy() {
					public Command getCommand(Request request) {
						if (understandsRequest(request)) {
							if (request instanceof CreateViewAndElementRequest) {
								CreateElementRequestAdapter adapter = ((CreateViewAndElementRequest) request)
										.getViewAndElementDescriptor()
										.getCreateElementRequestAdapter();
								IElementType type = (IElementType) adapter
										.getAdapter(IElementType.class);
								if (type == OntoUML.diagram.providers.OntoUMLElementTypes.Property_2012) {
									EditPart compartmentEditPart = getChildBySemanticHint(OntoUML.diagram.part.OntoUMLVisualIDRegistry
											.getType(OntoUML.diagram.edit.parts.StructuralDatatypeAttributeCompartmentEditPart.VISUAL_ID));
									return compartmentEditPart == null ? null
											: compartmentEditPart
													.getCommand(request);
								}
							}
							return super.getCommand(request);
						}
						return null;
					}
				});
		super.createDefaultEditPolicies();
		installEditPolicy(
				EditPolicyRoles.SEMANTIC_ROLE,
				new OntoUML.diagram.edit.policies.StructuralDatatypeItemSemanticEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		// XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
		// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
	}

	/**
	 * @generated
	 */
	protected LayoutEditPolicy createLayoutEditPolicy() {

		ConstrainedToolbarLayoutEditPolicy lep = new ConstrainedToolbarLayoutEditPolicy() {

			protected EditPolicy createChildEditPolicy(EditPart child) {
				if (child.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE) == null) {
					if (child instanceof ITextAwareEditPart) {
						return new OntoUML.diagram.edit.policies.OntoUMLTextSelectionEditPolicy();
					}
				}
				return super.createChildEditPolicy(child);
			}
		};
		return lep;
	}

	/**
	 * @generated
	 */
	protected IFigure createNodeShape() {
		StructuralDatatypeFigure figure = new StructuralDatatypeFigure();
		return primaryShape = figure;
	}

	/**
	 * @generated
	 */
	public StructuralDatatypeFigure getPrimaryShape() {
		return (StructuralDatatypeFigure) primaryShape;
	}

	/**
	 * @generated
	 */
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof OntoUML.diagram.edit.parts.WrappingLabel12EditPart) {
			((OntoUML.diagram.edit.parts.WrappingLabel12EditPart) childEditPart)
					.setLabel(getPrimaryShape()
							.getFigureStructuralDatatypeLabelFigure());
			return true;
		}
		if (childEditPart instanceof OntoUML.diagram.edit.parts.StructuralDatatypeNameEditPart) {
			((OntoUML.diagram.edit.parts.StructuralDatatypeNameEditPart) childEditPart)
					.setLabel(getPrimaryShape()
							.getFigureStructuralDatatypeNameLabelFigure());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean removeFixedChild(EditPart childEditPart) {

		return false;
	}

	/**
	 * @generated
	 */
	protected void addChildVisual(EditPart childEditPart, int index) {
		if (addFixedChild(childEditPart)) {
			return;
		}
		super.addChildVisual(childEditPart, -1);
	}

	/**
	 * @generated
	 */
	protected void removeChildVisual(EditPart childEditPart) {
		if (removeFixedChild(childEditPart)) {
			return;
		}
		super.removeChildVisual(childEditPart);
	}

	/**
	 * @generated
	 */
	protected IFigure getContentPaneFor(IGraphicalEditPart editPart) {

		return super.getContentPaneFor(editPart);
	}

	/**
	 * @generated
	 */
	protected NodeFigure createNodePlate() {
		DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(getMapMode()
				.DPtoLP(40), getMapMode().DPtoLP(40));
		return result;
	}

	/**
	 * Creates figure for this edit part.
	 * 
	 * Body of this method does not depend on settings in generation model
	 * so you may safely remove <i>generated</i> tag and modify it.
	 * 
	 * @generated
	 */
	protected NodeFigure createNodeFigure() {
		NodeFigure figure = createNodePlate();
		figure.setLayoutManager(new StackLayout());
		IFigure shape = createNodeShape();
		figure.add(shape);
		contentPane = setupContentPane(shape);
		return figure;
	}

	/**
	 * Default implementation treats passed figure as content pane.
	 * Respects layout one may have set for generated figure.
	 * @param nodeShape instance of generated figure class
	 * @generated
	 */
	protected IFigure setupContentPane(IFigure nodeShape) {
		if (nodeShape.getLayoutManager() == null) {
			ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
			layout.setSpacing(getMapMode().DPtoLP(5));
			nodeShape.setLayoutManager(layout);
		}
		return nodeShape; // use nodeShape itself as contentPane
	}

	/**
	 * @generated
	 */
	public IFigure getContentPane() {
		if (contentPane != null) {
			return contentPane;
		}
		return super.getContentPane();
	}

	/**
	 * @generated
	 */
	public EditPart getPrimaryChildEditPart() {
		return getChildBySemanticHint(OntoUML.diagram.part.OntoUMLVisualIDRegistry
				.getType(OntoUML.diagram.edit.parts.WrappingLabel12EditPart.VISUAL_ID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * Created to update the exibition of the meta-attributes.
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected void handleNotificationEvent(Notification event) {
		getPrimaryShape().updateContents(this);
		super.handleNotificationEvent(event);
	}

	/**
	 * @generated
	 */
	public class StructuralDatatypeFigure extends RectangleFigure {

		/**
		 * @generated
		 */
		private WrappingLabel fFigureStructuralDatatypeLabelFigure;
		/**
		 * @generated
		 */
		private WrappingLabel fFigureStructuralDatatypeNameLabelFigure;

		/**
		 * @generated
		 */
		public StructuralDatatypeFigure() {

			ToolbarLayout layoutThis = new ToolbarLayout();
			layoutThis.setStretchMinorAxis(true);
			layoutThis.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);

			layoutThis.setSpacing(0);
			layoutThis.setVertical(true);

			this.setLayoutManager(layoutThis);

			createContents();
		}

		/**
		 * @generated
		 */
		private void createContents() {

			fFigureStructuralDatatypeLabelFigure = new WrappingLabel();
			fFigureStructuralDatatypeLabelFigure
					.setText("<<StructuralDatatype>>");

			this.add(fFigureStructuralDatatypeLabelFigure);

			fFigureStructuralDatatypeNameLabelFigure = new WrappingLabel();
			fFigureStructuralDatatypeNameLabelFigure.setText("Name");

			this.add(fFigureStructuralDatatypeNameLabelFigure);

		}

		/**
		 * <!-- begin-user-doc -->
		 * Created to update the exibition of the meta-attributes.
		 * <!-- end-user-doc -->
		 * @generated NOT
		 */
		private void updateContents(
				StructuralDatatypeEditPart structuraldatatypeeditpart) {

			StructuralDatatype s = (StructuralDatatype) ((View) structuraldatatypeeditpart
					.getModel()).getElement();
			for (int i = 0; i < s.getAttribute().size(); i++) {
				Property p1 = (Property) s.getAttribute().get(i);
				if (p1.getAttribute().size() >= 2) {
					DatatypeAssociation d = (DatatypeAssociation) p1
							.getAttribute().get(0);
					Property p2 = (Property) p1.getAttribute().get(1);
					d.setOwner(null);
					d.setContainer(s.getContainer());
					d.setName("Name");
					p1.setAssociationEnd(d);
					p1.setName("Name");
					p2.setAssociationEnd(d);
					p2.setOwner(d);
					p2.setName("Name");
				}
			}
		}

		/**
		 * @generated
		 */
		private boolean myUseLocalCoordinates = false;

		/**
		 * @generated
		 */
		protected boolean useLocalCoordinates() {
			return myUseLocalCoordinates;
		}

		/**
		 * @generated
		 */
		protected void setUseLocalCoordinates(boolean useLocalCoordinates) {
			myUseLocalCoordinates = useLocalCoordinates;
		}

		/**
		 * @generated
		 */
		public WrappingLabel getFigureStructuralDatatypeLabelFigure() {
			return fFigureStructuralDatatypeLabelFigure;
		}

		/**
		 * @generated
		 */
		public WrappingLabel getFigureStructuralDatatypeNameLabelFigure() {
			return fFigureStructuralDatatypeNameLabelFigure;
		}

	}

}
