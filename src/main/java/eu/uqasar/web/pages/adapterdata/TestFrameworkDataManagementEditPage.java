package eu.uqasar.web.pages.adapterdata;

/*
 * #%L
 * U-QASAR
 * %%
 * Copyright (C) 2012 - 2015 U-QASAR Consortium
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.Date;

import javax.inject.Inject;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.joda.time.DateTime;

import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextField;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextFieldConfig;
import eu.uqasar.model.measure.TestLinkMetricMeasurement;
import eu.uqasar.service.dataadapter.TestLinkDataService;
import eu.uqasar.web.components.HtmlEvent;
import eu.uqasar.web.components.InputBorder;
import eu.uqasar.web.components.InputValidationForm;
import eu.uqasar.web.components.OnEventInputBeanValidationBorder;
import eu.uqasar.web.pages.AboutPage;
import eu.uqasar.web.pages.BasePage;

public class TestFrameworkDataManagementEditPage extends BasePage {

 // The tableEntity to edit/save
    protected TestLinkMetricMeasurement tableEntity;
    
    @Inject
    private TestLinkDataService testLinkService;
    
    private final Form<TestLinkMetricMeasurement> tableEntityForm;
    
    protected final InputBorder<String> projectNameBorder,projectKeyBorder,metricBorder,valueBorder;
    
    @SuppressWarnings("unused")
    private final DateTextField someDateField;
    public TestFrameworkDataManagementEditPage(PageParameters parameters) {
        super(parameters);
     // extract id parameter and set page title, header and tableEntity
        // depending on whether we are editing an existing tableEntity or
        // creating
        // a new one
        loadTableEntity(parameters.get("idForTableEntity"));
        
        // add form to create new tableEntity
        add(tableEntityForm = newTableEntityForm());
        
        // add text field for name inside a border component that performs bean
        // validation
        tableEntityForm.add(projectNameBorder = newProjectNameField());
        
        // add text field for name inside a border component that performs bean
        // validation
        tableEntityForm.add(projectKeyBorder = newProjectKeyField());
        
        
        // add text field for name inside a border component that performs bean
        // validation
        tableEntityForm.add(metricBorder = newMetricField());
        
        
        // add text field for name inside a border component that performs bean
        // validation
        tableEntityForm.add(valueBorder = newValueField());
         
        
        // add date text field for due date inside a border component that
        // performs bean validation plus a date picker component for easy date
        // selection
        tableEntityForm
                .add(newSomeDateTextField(someDateField = newDateTextField()));
        
        
        
        // add a button to create new tableEntity
        tableEntityForm.add(newSubmitLink(parameters));

        // add cancel button to return to tableEntity list page
        tableEntityForm.add(newCancelLink(parameters));
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @param idParam
     */
    protected void loadTableEntity(final StringValue idParam) {
        if (idParam.isEmpty()) {
            setPageTitle(new StringResourceModel("page.create.title", this,
                    null));
            add(new Label("header", new StringResourceModel(
                    "form.create.header", this, null)));
            tableEntity = new TestLinkMetricMeasurement();

        } else {
            setPageTitle(new StringResourceModel("page.edit.title", this, null));
            add(new Label("header", new StringResourceModel("form.edit.header",
                    this, null)));
            // set the tableEntity we got from previous page
            try {
                tableEntity = testLinkService.getById(idParam
                        .toOptionalLong());

            } catch (Exception e) {
                throw new RestartResponseException(AboutPage.class);
            }
        }
    }
    
    /**
     * 
     * @return
     */
    private Form<TestLinkMetricMeasurement> newTableEntityForm() {
        Form<TestLinkMetricMeasurement> form = new InputValidationForm<TestLinkMetricMeasurement>("form");
        form.setOutputMarkupId(true);
        return form;
    }
    
    
    /**
     * 
     * @return
     */
    private InputBorder<String> newProjectNameField() {
        return new OnEventInputBeanValidationBorder<String>("projectNameBorder",
                new TextField<String>("name", new PropertyModel<String>(
                        tableEntity, "name")), new StringResourceModel(
                        "name.input.label", this, null),
                HtmlEvent.ONCHANGE);
    }
    
    /**
     * 
     * @return
     */
    private InputBorder<String> newProjectKeyField() {
        return new OnEventInputBeanValidationBorder<String>("projectKeyBorder",
                new TextField<String>("testLinkKey", new PropertyModel<String>(
                        tableEntity, "testLinkKey")), new StringResourceModel(
                        "testLinkKey.input.label", this, null),
                HtmlEvent.ONCHANGE);
    }
    
    /**
     * 
     * @return
     */
    private InputBorder<String> newMetricField(){
        return new OnEventInputBeanValidationBorder<String>("metricBorder",
            new TextField<String>("testLinkMetric", new PropertyModel<String>(
                    tableEntity, "testLinkMetric")), new StringResourceModel(
                    "testLinkMetric.input.label", this, null),
            HtmlEvent.ONCHANGE);
    }
    
    /**
     * 
     * @return
     */
    private InputBorder<String> newValueField(){
        return new OnEventInputBeanValidationBorder<String>("valueBorder",
            new org.apache.wicket.markup.html.form.TextArea<String>("value", new PropertyModel<String>(
                    tableEntity, "value")), new StringResourceModel(
                    "value.input.label", this, null),
            HtmlEvent.ONCHANGE);
    }
    
    /**
     * 
     * @param someDateTextField
     * @return
     */
    private InputBorder<Date> newSomeDateTextField(
            final DateTextField someDateTextField) {
        return new OnEventInputBeanValidationBorder<Date>(
                "someDateValidationBorder", someDateTextField,
                new StringResourceModel("some.date.input.label", this, null),
                HtmlEvent.ONCHANGE);
    }
    
    /**
     * 
     * @return
     */
    private DateTextField newDateTextField() {
        DateTextFieldConfig config = new DateTextFieldConfig()
                .withFormat("dd.MM.yyyy")
                .withStartDate(new DateTime().withYear(1900))
                .allowKeyboardNavigation(true).autoClose(true)
                .highlightToday(false).showTodayButton(false);
        DateTextField dateTextField = new DateTextField("timeStamp",
                new PropertyModel<Date>(tableEntity, "timeStamp"), config);
        return dateTextField;
    }
    
    /**
     * 
     * @return
     */
    private AjaxSubmitLink newSubmitLink( final PageParameters parameters) {
        return new AjaxSubmitLink("submit", tableEntityForm) {
            private static final long serialVersionUID = -8233439456118623954L;

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                save(target, parameters);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                showErrors(target);
            }
        };
    }
    
    /**
     * 
     */
    protected void save(AjaxRequestTarget target, PageParameters parameters) {
        // save tableEntity
        saveTableEntity();
        // success message has to be associated to session so that it is shown
        // in the global feedback panel
        Session.get().success(
                new StringResourceModel("saved.message", this, Model
                        .of(tableEntity)).getString());
        
        // redirect to tableEntity list page
        setResponsePage(
            TestFrameworkDataManagementPage.class,
            parameters);
    }
    
    /**
     * 
     */
    private boolean saveTableEntity() {
        // tableEntity.setCompanyAddress(tableEntityAddress);
        // tableEntity.setEmployee(tableEntityEmployee);
        testLinkService.create(tableEntity);

        return true;
    }
    
    /**
     * 
     * @param target
     */
    protected void showErrors(AjaxRequestTarget target) {
        // in case of errors (e.g. validation errors) show error
        // messages in form
        target.add(tableEntityForm);
    }
    
    /**
     * 
     * @return
     */
    private Link<TestFrameworkDataManagementPage> newCancelLink(final PageParameters parameters) {
        return new Link<TestFrameworkDataManagementPage>("cancel") {
            private static final long serialVersionUID = -310533532532643267L;

            @Override
            public void onClick() {
                setResponsePage(TestFrameworkDataManagementPage.class,parameters);
            }
        };
    }
}
