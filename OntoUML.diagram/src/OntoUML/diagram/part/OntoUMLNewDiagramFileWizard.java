package OntoUML.diagram.part;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateDiagramViewOperation;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

/**
 * @generated
 */
public class OntoUMLNewDiagramFileWizard extends Wizard {

	/**
	 * @generated
	 */
	private OntoUML.diagram.application.WizardNewFileCreationPage myFileCreationPage;

	/**
	 * @generated
	 */
	private OntoUML.diagram.part.ModelElementSelectionPage diagramRootElementSelectionPage;

	/**
	 * @generated
	 */
	private TransactionalEditingDomain myEditingDomain;

	/**
	 * @generated
	 */
	public OntoUMLNewDiagramFileWizard(URI domainModelURI, EObject diagramRoot,
			TransactionalEditingDomain editingDomain) {
		assert domainModelURI != null : "Domain model uri must be specified"; //$NON-NLS-1$
		assert diagramRoot != null : "Doagram root element must be specified"; //$NON-NLS-1$
		assert editingDomain != null : "Editing domain must be specified"; //$NON-NLS-1$

		myFileCreationPage = new OntoUML.diagram.application.WizardNewFileCreationPage(
				OntoUML.diagram.part.Messages.OntoUMLNewDiagramFileWizard_CreationPageName,
				StructuredSelection.EMPTY);
		myFileCreationPage
				.setTitle(OntoUML.diagram.part.Messages.OntoUMLNewDiagramFileWizard_CreationPageTitle);
		myFileCreationPage
				.setDescription(NLS
						.bind(
								OntoUML.diagram.part.Messages.OntoUMLNewDiagramFileWizard_CreationPageDescription,
								OntoUML.diagram.edit.parts.ContainerEditPart.MODEL_ID));
		IPath filePath;
		String fileName = domainModelURI.trimFileExtension().lastSegment();
		if (domainModelURI.isPlatformResource()) {
			filePath = new Path(domainModelURI.trimSegments(1)
					.toPlatformString(true));
		} else if (domainModelURI.isFile()) {
			filePath = new Path(domainModelURI.trimSegments(1).toFileString());
		} else {
			// TODO : use some default path
			throw new IllegalArgumentException(
					"Unsupported URI: " + domainModelURI); //$NON-NLS-1$
		}
		myFileCreationPage.setContainerFullPath(filePath);
		myFileCreationPage
				.setFileName(OntoUML.diagram.part.OntoUMLDiagramEditorUtil
						.getUniqueFileName(filePath, fileName,
								"ontouml_diagram")); //$NON-NLS-1$

		diagramRootElementSelectionPage = new DiagramRootElementSelectionPage(
				OntoUML.diagram.part.Messages.OntoUMLNewDiagramFileWizard_RootSelectionPageName);
		diagramRootElementSelectionPage
				.setTitle(OntoUML.diagram.part.Messages.OntoUMLNewDiagramFileWizard_RootSelectionPageTitle);
		diagramRootElementSelectionPage
				.setDescription(OntoUML.diagram.part.Messages.OntoUMLNewDiagramFileWizard_RootSelectionPageDescription);
		diagramRootElementSelectionPage.setModelElement(diagramRoot);

		myEditingDomain = editingDomain;
	}

	/**
	 * @generated
	 */
	public void addPages() {
		addPage(myFileCreationPage);
		addPage(diagramRootElementSelectionPage);
	}

	/**
	 * @generated
	 */
	public boolean performFinish() {
		List affectedFiles = new LinkedList();
		IPath diagramModelPath = myFileCreationPage.getContainerFullPath()
				.append(myFileCreationPage.getFileName());
		URI diagramModelURI = URI.createFileURI(diagramModelPath.toString());
		ResourceSet resourceSet = myEditingDomain.getResourceSet();
		final Resource diagramResource = resourceSet
				.createResource(diagramModelURI);
		AbstractTransactionalCommand command = new AbstractTransactionalCommand(
				myEditingDomain,
				OntoUML.diagram.part.Messages.OntoUMLNewDiagramFileWizard_InitDiagramCommand,
				affectedFiles) {

			protected CommandResult doExecuteWithResult(
					IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {
				int diagramVID = OntoUML.diagram.part.OntoUMLVisualIDRegistry
						.getDiagramVisualID(diagramRootElementSelectionPage
								.getModelElement());
				if (diagramVID != OntoUML.diagram.edit.parts.ContainerEditPart.VISUAL_ID) {
					return CommandResult
							.newErrorCommandResult(OntoUML.diagram.part.Messages.OntoUMLNewDiagramFileWizard_IncorrectRootError);
				}
				Diagram diagram = ViewService
						.createDiagram(
								diagramRootElementSelectionPage
										.getModelElement(),
								OntoUML.diagram.edit.parts.ContainerEditPart.MODEL_ID,
								OntoUML.diagram.part.OntoUMLDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				diagramResource.getContents().add(diagram);
				return CommandResult.newOKCommandResult();
			}
		};
		try {
			OperationHistoryFactory.getOperationHistory().execute(command,
					new NullProgressMonitor(), null);
			diagramResource.save(OntoUML.diagram.part.OntoUMLDiagramEditorUtil
					.getSaveOptions());
			OntoUML.diagram.part.OntoUMLDiagramEditorUtil
					.openDiagram(diagramResource);
		} catch (ExecutionException e) {
			OntoUML.diagram.part.OntoUMLDiagramEditorPlugin.getInstance()
					.logError("Unable to create model and diagram", e); //$NON-NLS-1$
		} catch (IOException ex) {
			OntoUML.diagram.part.OntoUMLDiagramEditorPlugin
					.getInstance()
					.logError(
							"Save operation failed for: " + diagramModelURI, ex); //$NON-NLS-1$
		} catch (PartInitException ex) {
			OntoUML.diagram.part.OntoUMLDiagramEditorPlugin.getInstance()
					.logError("Unable to open editor", ex); //$NON-NLS-1$
		}
		return true;
	}

	/**
	 * @generated
	 */
	private static class DiagramRootElementSelectionPage extends
			OntoUML.diagram.part.ModelElementSelectionPage {

		/**
		 * @generated
		 */
		protected DiagramRootElementSelectionPage(String pageName) {
			super(pageName);
		}

		/**
		 * @generated
		 */
		protected String getSelectionTitle() {
			return OntoUML.diagram.part.Messages.OntoUMLNewDiagramFileWizard_RootSelectionPageSelectionTitle;
		}

		/**
		 * @generated
		 */
		protected boolean validatePage() {
			if (selectedModelElement == null) {
				setErrorMessage(OntoUML.diagram.part.Messages.OntoUMLNewDiagramFileWizard_RootSelectionPageNoSelectionMessage);
				return false;
			}
			boolean result = ViewService
					.getInstance()
					.provides(
							new CreateDiagramViewOperation(
									new EObjectAdapter(selectedModelElement),
									OntoUML.diagram.edit.parts.ContainerEditPart.MODEL_ID,
									OntoUML.diagram.part.OntoUMLDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT));
			setErrorMessage(result ? null
					: OntoUML.diagram.part.Messages.OntoUMLNewDiagramFileWizard_RootSelectionPageInvalidSelectionMessage);
			return result;
		}
	}
}