package org.dynaresume.eclipse.ui.dialogs;

import org.dynaresume.domain.hr.Resume;
import org.dynaresume.services.ResumeService;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.rap.singlesourcing.SingleSourcingUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.springframework.data.domain.Page;

import fr.opensagres.eclipse.forms.widgets.pagination.spring.PageableController;
import fr.opensagres.eclipse.forms.widgets.pagination.spring.PageableTable;
import fr.opensagres.xdocreport.eclipse.ui.dialogs.SearchDialog;

public class SearchResumeDialog extends SearchDialog {

	public final static String ID = "org.dynaresume.eclipse.ui.dialogs.SearchResumeDialog";

	private ResumeService resumeService;

	private String firstNameCriteria;
	private String lastNameCriteria;

	private PageableTable paginationTable;

	public void setResumeService(ResumeService resumeService) {
		this.resumeService = resumeService;
	}

	// private TableViewer viewer;

	public SearchResumeDialog() {
		super();
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		Composite parent = managedForm.getForm().getBody();
		GridLayout layout = new GridLayout();
		parent.setLayout(layout);

		FormToolkit toolkit = managedForm.getToolkit();
		createSearchCriteriaContainer(toolkit, parent, managedForm);
		createSearchResultContainer(toolkit, parent);

		SingleSourcingUtils.FormToolkit_paintBordersFor(
				managedForm.getToolkit(), parent);
	}

	private void createSearchCriteriaContainer(FormToolkit toolkit,
			Composite parent, final IManagedForm managedForm) {
		Group group = new Group(parent, SWT.SHADOW_ETCHED_IN);
		group.setLayoutData(new GridData(GridData.FILL_BOTH));
		group.setLayout(new GridLayout());

		group.setText("Resume search criteria");
		toolkit.adapt(group);

		Composite container = toolkit.createComposite(group);
		container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		container.setLayout(new GridLayout(4, false));

		toolkit.createLabel(container, "First name:");
		final Text firstNameText = toolkit
				.createText(container, "", SWT.BORDER);
		firstNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		toolkit.createLabel(container, "Last name:");
		final Text lastNameText = toolkit.createText(container, "", SWT.BORDER);
		lastNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Button searchButton = toolkit.createButton(container, "Search",
				SWT.BORDER);
		searchButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				firstNameCriteria = firstNameText.getText();
				lastNameCriteria = lastNameText.getText();
				paginationTable.refreshPage();
			}
		});
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 4;
		data.horizontalAlignment = GridData.END;
		searchButton.setLayoutData(data);

		// section.setClient(container);
	}

	private void createSearchResultContainer(FormToolkit toolkit,
			Composite parent) {
		Section section = toolkit.createSection(parent, Section.DESCRIPTION
				| Section.TITLE_BAR | Section.EXPANDED);
		// td = new TableWrapData(TableWrapData.FILL);
		// td.colspan = 2;
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		section.setLayout(layout);

		section.setLayoutData(new GridData(GridData.FILL_BOTH));
		// section.addExpansionListener(new ExpansionAdapter() {
		// public void expansionStateChanged(ExpansionEvent e) {
		// managedForm.getForm().reflow(true);
		// }
		// });
		section.setText("Resume search results");
		section.setDescription("Select one or several resume from the below list of resume search results and click OK to open the selected resumes.");

		Composite container = toolkit.createComposite(section);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		layout = new GridLayout();
		layout.marginHeight = 0;
		container.setLayout(layout);

		paginationTable = new PageableTable(container, SWT.NONE, toolkit) {

			@Override
			protected Page<Resume> loadPage(PageableController controller) {
				// TODO Auto-generated method stub
				return resumeService.findByFirstNameAndLastName(
						firstNameCriteria, lastNameCriteria, controller);
			}

			@Override
			protected int getTableStyle() {
				return SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL
						| SWT.FULL_SELECTION | SWT.BORDER;
			}
		};
		paginationTable.setLayoutData(new GridData(GridData.FILL_BOTH));

		TableViewer viewer = paginationTable.getViewer();
		createColumns(viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		viewer.setContentProvider(new ArrayContentProvider());

		paginationTable.setCurrentPage(0);
		section.setClient(container);
	}

	// private void createViewer(Composite parent) {
	// viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
	// | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
	// createColumns(parent, viewer);
	// final Table table = viewer.getTable();
	// table.setHeaderVisible(true);
	// table.setLinesVisible(true);
	//
	// viewer.setContentProvider(new ArrayContentProvider());
	// // ResumeService resumeService = (ResumeService)
	// // PlatformUI.getWorkbench()
	// // .getService(ResumeService.class);
	// // Get the content for the viewer, setInput will call getElements in the
	// // contentProvider
	// viewer.setInput(resumeService.findAll());
	//
	// // Layout the viewer
	// GridData gridData = new GridData();
	// gridData.verticalAlignment = GridData.FILL;
	// gridData.horizontalSpan = 2;
	// gridData.grabExcessHorizontalSpace = true;
	// gridData.grabExcessVerticalSpace = true;
	// gridData.horizontalAlignment = GridData.FILL;
	// viewer.getControl().setLayoutData(gridData);
	// }
	//
	// public TableViewer getViewer() {
	// return viewer;
	// }

	// This will create the columns for the table
	private void createColumns(final TableViewer viewer) {
		String[] titles = { "First name", "Last name" };
		int[] bounds = { 180, 180 };

		// First column is for the first name
		TableViewerColumn col = createTableViewerColumn(viewer, titles[0],
				bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Resume p = (Resume) element;
				return p.getOwner().getFirstName();
			}
		});

		// Second column is for the last name
		col = createTableViewerColumn(viewer, titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Resume p = (Resume) element;
				return p.getOwner().getLastName();
			}
		});

		// // Now the gender
		// col = createTableViewerColumn(titles[2], bounds[2], 2);
		// col.setLabelProvider(new ColumnLabelProvider() {
		// @Override
		// public String getText(Object element) {
		// return "Edit";
		// }
		// });
		//
		// // // Now the status married
		// col = createTableViewerColumn(titles[3], bounds[3], 3);
		// col.setLabelProvider(new ColumnLabelProvider() {
		// @Override
		// public String getText(Object element) {
		// return "Open";
		// }
		//
		// });

	}

	private TableViewerColumn createTableViewerColumn(TableViewer viewer,
			String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;

	}

	/*
	 * Overrides method from Dialog
	 */
	protected void okPressed() {
		// Build a list of selected children.
		IStructuredSelection selection = (IStructuredSelection) paginationTable
				.getViewer().getSelection();
		setResult(selection.toList());
		super.okPressed();
	}

}
