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

import java.util.*;

import org.eclipse.ui.internal.ActionExpression;

/**
 */
public class NavigatorRegistry {
	// keyed by target id, value = rootContentDescriptor
	private Map rootContentDescriptors;
/**
 * Create a new ViewRegistry.
 */
public NavigatorRegistry() {
	rootContentDescriptors = new HashMap();
}

/**
 */
protected void add(NavigatorDescriptor descriptor) {
	NavigatorRootContentDescriptor rootContentDescriptor = descriptor.getRootContentDescriptor();
	NavigatorContentDescriptor contentDescriptor = descriptor.getContentDescriptor();
	String viewTargetId = descriptor.getTargetId();
	
	if (rootContentDescriptor != null)
		rootContentDescriptors.put(viewTargetId, rootContentDescriptor);	
	else 
		rootContentDescriptor = getRootContentDescriptor(viewTargetId);
		
	if (contentDescriptor != null) {
		rootContentDescriptor.addSubContentDescriptor(contentDescriptor);
	}
}
public NavigatorContentDescriptor getContentProviderDescriptor(NavigatorRootContentDescriptor root, Object element) {
	ArrayList descriptors = getChildContentDescriptors(root);
	NavigatorContentDescriptor contentDescriptor = null;
	int priority = -1;
	
	for (int i = 0; i < descriptors.size(); i++) {
		NavigatorContentDescriptor descriptor = (NavigatorContentDescriptor)descriptors.get(i);
		ActionExpression enablement = descriptor.getEnableExpression(); 	
		if (enablement == null) {
			if (descriptor.getPriority() > priority) contentDescriptor = descriptor;
		} else if (enablement.isEnabledFor(element) && descriptor.getPriority() > priority) {
			priority = descriptor.getPriority();
			contentDescriptor = descriptor;
		}
	}
	return contentDescriptor;	
}
public ArrayList getChildContentDescriptors(NavigatorRootContentDescriptor root) {
	return root.getChildContentDescriptors();
}
public NavigatorRootContentDescriptor getRootContentDescriptor(String partId) {
	return (NavigatorRootContentDescriptor) rootContentDescriptors.get(partId);
}

}