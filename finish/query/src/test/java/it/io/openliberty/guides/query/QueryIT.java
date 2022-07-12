// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2021 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
// end::copyright[]
package it.io.openliberty.guides.query;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.provider.jsrjsonp.JsrJsonpProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class QueryIT {

    private static final String URL = "http://localhost:9081/";
    private static Client client;

    @BeforeAll
    private static void setup() {
        client = ClientBuilder.newClient();
        client.register(JsrJsonpProvider.class);
    }

    @AfterAll
    private static void teardown() {
        client.close();
    }

    @Test
    @Order(1)
    public void testGetPropertiesString() throws MalformedURLException {
        WebTarget target = client.target(URL + "query/properties/os.name");
        Response response = target.request().get();
        assertEquals(200, response.getStatus(),
                     "Incorrect response code from " + target.getUri().getPath());
        response.close();
    }

    @Test
    @Order(2)
    public void testGetOSProperties() throws MalformedURLException {
        WebTarget target = client.target(URL + "query/properties/os");
        Response response = target.request().get();
        assertEquals(200, response.getStatus(),
                     "Incorrect response code from " + target.getUri().getPath());
        response.close();
    }
    @Test
    @Order(3)
    public void testGetUserProperties() throws MalformedURLException {
        WebTarget target = client.target(URL + "query/properties/user");
        Response response = target.request().get();
        assertEquals(200, response.getStatus(),
                     "Incorrect response code from " + target.getUri().getPath());
        response.close();
    }
    @Test
    @Order(4)
    public void testGetJavaProperties() throws MalformedURLException {
        WebTarget target = client.target(URL + "query/properties/java");
        Response response = target.request().get();
        assertEquals(200, response.getStatus(),
                     "Incorrect response code from " + target.getUri().getPath());
        response.close();
    }
}