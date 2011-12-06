/*******************************************************************************
 * Copyright (C) 2011 Angelo Zerr <angelo.zerr@gmail.com>, Pascal Leclercq <pascal.leclercq@gmail.com>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo ZERR - initial API and implementation
 *     Pascal Leclercq - initial API and implementation
 *******************************************************************************/
package org.eclipse.nebula.widgets.picture;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;

/**
 * {@link AbstractPictureControl} implementation with SWT {@link Link}.
 */
public class PictureControl extends AbstractPictureControl<Link> {

	private static final String END_HREF = "</a>";
	private static final String START_HREF = "<a href=\"#\" >";

	/**
	 * Constructor for {@link PictureControl} with given SWT style .
	 * 
	 * @param parent
	 *            a composite control which will be the parent of the new
	 *            instance (cannot be null)
	 * 
	 * @param compositeStyle
	 *            SWT style of the SWT Composite which host Label+Link controls.
	 * @param labelStyle
	 *            SWT style of the Label control.
	 * @param linkStyle
	 *            SWT style of the Link control.
	 */
	public PictureControl(Composite parent, int compositeStyle, int labelStyle,
			int linkStyle) {
		super(parent, compositeStyle, labelStyle, linkStyle);
	}

	/**
	 * Constructor for {@link PictureControl} with default SWT styles.
	 * 
	 * @param parent
	 *            a composite control which will be the parent of the new
	 *            instance (cannot be null)
	 */
	public PictureControl(Composite parent) {
		super(parent);
	}

	@Override
	protected Label createLabel(Composite parent, int style) {
		return new Label(parent, style);
	}

	@Override
	protected Link createLink(Composite parent, int style) {
		return new Link(parent, style);
	}

	@Override
	protected Composite createComposite(Composite parent, int style) {
		return new Composite(parent, style);
	}

	@Override
	protected void setLinkText(Link modifyImageLink, String text) {
		StringBuilder href = new StringBuilder();
		href.append(START_HREF);
		href.append(text);
		href.append(END_HREF);
		modifyImageLink.setText(href.toString());
	}

	@Override
	protected void addModifyImageHandler(Link modifyImageLink) {
		modifyImageLink.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				handleModifyImage();
			}
		});
	}

	@Override
	protected void addDeleteImageHandler(Link deleteImageLink) {
		deleteImageLink.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				handleDeleteImage();
			}
		});
		
	}
}