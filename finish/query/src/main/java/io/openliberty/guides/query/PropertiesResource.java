// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2022, 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
// end::copyright[]
package io.openliberty.guides.query;

import java.util.Properties;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.openliberty.guides.query.client.SystemServiceClient;

@ApplicationScoped
@Path("/properties")
public class PropertiesResource {

    @Inject
    @ConfigProperty(name = "system.hostname", defaultValue = "localhost")
    String SYSTEM_HOST;

    @Inject
    @ConfigProperty(name = "system.port", defaultValue = "9080")
    int SYSTEM_PORT;

    // tag::unary[]
    @GET
    @Path("/{property}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPropertiesString(@PathParam("property") String property) {
        // tag::createClient1[]
        SystemServiceClient client = new SystemServiceClient(SYSTEM_HOST, SYSTEM_PORT);
        // end::createClient1[]
        try {
            return client.getProperty(property);
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        } finally {
            client.shutdown();
        }
    }
    // end::unary[]

    // tag::serverStreaming[]
    @GET
    @Path("/os")
    @Produces(MediaType.APPLICATION_JSON)
    public Properties getOSProperties() {
        // tag::createClient2[]
        SystemServiceClient client = new SystemServiceClient(SYSTEM_HOST, SYSTEM_PORT);
        // end::createClient2[]
        Properties properties = new Properties();
        try {
            properties = client.getServerStreamingProperties("os.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
        return properties;
    }
    // end::serverStreaming[]

    // tag::clientStreaming[]
    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Properties getUserProperties() {
        // tag::createClient3[]
        SystemServiceClient client = new SystemServiceClient(SYSTEM_HOST, SYSTEM_PORT);
        // end::createClient3[]
        Properties properties = new Properties();
        try {
            return client.getClientStreamingProperties("user.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
        return properties;
    }
    // end::clientStreaming[]

    // tag::bidirectionalStreaming[]
    @GET
    @Path("/java")
    @Produces(MediaType.APPLICATION_JSON)
    public Properties getJavaProperties() {
        // tag::createClient4[]
        SystemServiceClient client = new SystemServiceClient(SYSTEM_HOST, SYSTEM_PORT);
        // end::createClient4[]
        Properties properties = new Properties();
        try {
            return client.getBidirectionalProperties("java.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
        return properties;
    }
    // end::bidirectionalStreaming[]
}
