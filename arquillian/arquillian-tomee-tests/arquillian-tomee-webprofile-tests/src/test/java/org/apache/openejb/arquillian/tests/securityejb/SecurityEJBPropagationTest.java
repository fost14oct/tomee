/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.openejb.arquillian.tests.securityejb;

import org.apache.openejb.arquillian.common.IO;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class SecurityEJBPropagationTest {
    @Deployment(testable = false)
    public static Archive<?> app() {
        return ShrinkWrap.create(WebArchive.class, "jaspic-ejb.war")
                .addClasses(
                        TheAuthConfigProvider.class, TheEJb.class, TheServlet.class, Init.class,
                        TheServerAuthConfig.class, TheServerAuthContext.class, TheServerAuthModule.class, TheServerAuthModule.class);
    }

    @ArquillianResource
    private URL base;

    @Test
    @Ignore("we need to upgrade to tomcat 8.5.1 cause org.apache.catalina.connector.Response.applicationResponse init should be null and not this")
    public void run() throws IOException {
        assertEquals("testtestnullguest", IO.slurp(new URL(base.toExternalForm() + "test?doLogin=true")));
    }
}