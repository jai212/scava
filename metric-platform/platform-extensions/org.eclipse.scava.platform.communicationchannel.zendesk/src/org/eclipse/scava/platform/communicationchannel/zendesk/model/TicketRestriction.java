/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.communicationchannel.zendesk.model;

public enum TicketRestriction {
    ORGANIZATION("organization"),
    GROUPS("groups"),
    ASSIGNED("assigned"),
    REQUESTED("requested");

    private final String name;

    private TicketRestriction(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
