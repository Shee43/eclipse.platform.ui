/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal.registry;

import java.util.ArrayList;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ui.WorkbenchException;

/**
 * 
 * @since 3.0
 */
public class NavigatorRootContentDescriptor extends NavigatorAbstractContentDescriptor {
	private ArrayList childContentDescriptors = new ArrayList();
	/**
	 * Creates a descriptor from a configuration element.
	 * 
	 * @param configElement configuration element to create a descriptor from
	 */
	public NavigatorRootContentDescriptor(IConfigurationElement configElement) throws WorkbenchException {
		super(configElement);
		readConfigElement();
	}
	protected void addSubContentDescriptor(NavigatorContentDescriptor descriptor) {
		childContentDescriptors.add(descriptor);
	}
	/**
	 */
	protected NavigatorAbstractContentDescriptor findContentDescriptor(String contentProviderId) {
		for (int i=0; i<childContentDescriptors.size(); i++) {
			NavigatorContentDescriptor descriptor = (NavigatorContentDescriptor)childContentDescriptors.get(i);
			if (descriptor.getId().equals(contentProviderId)) return descriptor;
		}
		return null;
	}
	protected ArrayList getChildContentDescriptors() {
		return childContentDescriptors;
	}
}
