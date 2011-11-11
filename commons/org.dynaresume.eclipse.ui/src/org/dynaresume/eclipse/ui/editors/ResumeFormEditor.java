package org.dynaresume.eclipse.ui.editors;

import org.dynaresume.domain.hr.Resume;
import org.dynaresume.services.ResumeService;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import fr.opensagres.xdocreport.eclipse.ui.editors.ModelAndEntryEditorInput;
import fr.opensagres.xdocreport.eclipse.ui.editors.ReportingFormEditor;

public class ResumeFormEditor extends ReportingFormEditor<Resume> {

	public static final String ID = "fr.opensagres.xdocreport.eclipse.demo.resume.editor.ResumeFormEditorPart";

	@Override
	protected void doAddPages() {
		try {
			addPage(new OverviewPage(this));
			addPage(new EducationsPage(this));
			addPage(new ExperiencesPage(this));
			addPage(new SkillsPage(this));
			addPage(new HobbiesPage(this));
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Resume onLoad(ModelAndEntryEditorInput<Resume> input) {
		
		ResumeService resumeService=(ResumeService)PlatformUI.getWorkbench().getService(ResumeService.class);
		return resumeService.findById(
				input.getModel().getId());
	}

	@Override
	protected Resume onSave(Resume resume, IProgressMonitor monitor) {
		ResumeService resumeService=(ResumeService)PlatformUI.getWorkbench().getService(ResumeService.class);
		return resumeService.save(resume);
	}

}