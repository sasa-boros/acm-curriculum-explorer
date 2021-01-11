package uns.ac.rs.metahandserver.ontology;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntProperty;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AcmOntologyPopulator {

    private static final String ACM_INDIVIDUALS_FEED_FILE = "sec_ontology_individuals.xlsx";
    private static final String ACM_URI_PREFIX = "http://www.semanticweb.org/sasaboros/ontologies/2020/11/sec_ontology#";

    public static void populateOntology(OntModel om) throws IOException {
        FileInputStream excelFile = new FileInputStream(new ClassPathResource(ACM_INDIVIDUALS_FEED_FILE).getFile());
        Workbook workbook = new XSSFWorkbook(excelFile);

        // classes
        OntClass course = om.getResource(ACM_URI_PREFIX + "Course").as(OntClass.class);
        OntClass knowledgeArea = om.getResource(ACM_URI_PREFIX + "KnowledgeArea").as(OntClass.class);
        OntClass knowledgeUnit = om.getResource(ACM_URI_PREFIX + "KnowledgeUnit").as(OntClass.class);
        OntClass learningResource = om.getResource(ACM_URI_PREFIX + "LearningResource").as(OntClass.class);
        OntClass learningOutcome = om.getResource(ACM_URI_PREFIX + "LearningOutcome").as(OntClass.class);

        // data properties
        OntProperty nameProperty = om.getProperty(ACM_URI_PREFIX + "name").as(OntProperty.class);
        OntProperty descriptionProperty = om.getProperty(ACM_URI_PREFIX + "description").as(OntProperty.class);
        OntProperty estimatedContactHoursProperty = om.getProperty(ACM_URI_PREFIX + "estimatedContactHours").as(OntProperty.class);
        OntProperty authorProperty = om.getProperty(ACM_URI_PREFIX + "author").as(OntProperty.class);
        OntProperty difficultyLevelProperty = om.getProperty(ACM_URI_PREFIX + "description").as(OntProperty.class);
        OntProperty teacherProperty = om.getProperty(ACM_URI_PREFIX + "teacher").as(OntProperty.class);
        OntProperty formatProperty = om.getProperty(ACM_URI_PREFIX + "format").as(OntProperty.class);
        OntProperty levelOfStudyProperty = om.getProperty(ACM_URI_PREFIX + "levelOfStudyProperty").as(OntProperty.class);

        // object properties
        OntProperty consistsOf = om.getResource(ACM_URI_PREFIX + "consistsOf").as(OntProperty.class);
        OntProperty includes = om.getResource(ACM_URI_PREFIX + "includes").as(OntProperty.class);
        OntProperty isTaughtUsing = om.getResource(ACM_URI_PREFIX + "isTaughtUsing").as(OntProperty.class);
        OntProperty obtainedBy = om.getResource(ACM_URI_PREFIX + "obtainedBy").as(OntProperty.class);
        OntProperty teaches = om.getResource(ACM_URI_PREFIX + "teaches").as(OntProperty.class);

        Iterator<Row> knowledgeAreaIterator = workbook.getSheet("KnowledgeAreas").rowIterator();
        Map<String, Individual> knowledgeAreas = new HashMap<>();
        while (knowledgeAreaIterator.hasNext()) {
            Row currentRow = knowledgeAreaIterator.next();

            String id = currentRow.getCell(0).getStringCellValue();
            String name = currentRow.getCell(1).getStringCellValue();
            int estimatedContactHours = (int) currentRow.getCell(2).getNumericCellValue();

            Individual individual = knowledgeArea.createIndividual(ACM_URI_PREFIX + id);
            individual.addLiteral(nameProperty,  om.createTypedLiteral(name));
            individual.addLiteral(estimatedContactHoursProperty,  om.createTypedLiteral(estimatedContactHours));
            knowledgeAreas.put(id, individual);
        }
        Iterator<Row> knowledgeUnitIterator = workbook.getSheet("KnowledgeUnits").rowIterator();
        Map<String, Individual> knowledgeUnits = new HashMap<>();
        while (knowledgeUnitIterator.hasNext()) {
            Row currentRow = knowledgeUnitIterator.next();

            String id = currentRow.getCell(0).getStringCellValue();
            String name = currentRow.getCell(1).getStringCellValue();
            String knowledgeAreaId = currentRow.getCell(2).getStringCellValue();

            Individual individual = knowledgeUnit.createIndividual(ACM_URI_PREFIX + id);
            individual.addLiteral(nameProperty,  om.createTypedLiteral(name));

            Individual knowledgeAreaIndividual = knowledgeAreas.get(knowledgeAreaId);
            knowledgeAreaIndividual.addProperty(consistsOf, individual);

            knowledgeUnits.put(id, individual);
        }
        Iterator<Row> learningOutcomeIterator = workbook.getSheet("LearningOutcomes").rowIterator();
        Map<String, Individual> learningOutcomes = new HashMap<>();
        while (learningOutcomeIterator.hasNext()) {
            Row currentRow = learningOutcomeIterator.next();

            String id = currentRow.getCell(0).getStringCellValue();
            String description = currentRow.getCell(1).getStringCellValue();
            String knowledgeUnitId = currentRow.getCell(2).getStringCellValue();

            Individual individual = learningOutcome.createIndividual(ACM_URI_PREFIX + id);
            individual.addLiteral(descriptionProperty,  om.createTypedLiteral(description));

            Individual knowledgeUnitIndividual = knowledgeUnits.get(knowledgeUnitId);
            knowledgeUnitIndividual.addProperty(includes, individual);

            learningOutcomes.put(id, individual);
        }
        Iterator<Row> courseIterator = workbook.getSheet("Courses").rowIterator();
        Map<String, Individual> courses = new HashMap<>();
        while (courseIterator.hasNext()) {
            Row currentRow = courseIterator.next();

            String id = currentRow.getCell(0).getStringCellValue();
            String name = currentRow.getCell(1).getStringCellValue();
            String[] learningOutcomeIds = currentRow.getCell(2).getStringCellValue().split(";");
            int difficultyLevel = (int) currentRow.getCell(3).getNumericCellValue();
            int levelOfStudy = (int) currentRow.getCell(4).getNumericCellValue();
            String teacher = currentRow.getCell(5).getStringCellValue();

            Individual individual = course.createIndividual(ACM_URI_PREFIX + id);
            individual.addLiteral(nameProperty,  om.createTypedLiteral(name));
            individual.addLiteral(difficultyLevelProperty,  om.createTypedLiteral(difficultyLevel));
            individual.addLiteral(levelOfStudyProperty,  om.createTypedLiteral(levelOfStudy));
            individual.addLiteral(teacherProperty,  om.createTypedLiteral(teacher));

            for (String loId : learningOutcomeIds) {
                Individual learningOutcomeIndividual = learningOutcomes.get(loId);
                individual.addProperty(teaches, learningOutcomeIndividual);
            }

            courses.put(id, individual);
        }
        Iterator<Row> learningResourceIterator = workbook.getSheet("LearningResources").rowIterator();
        Map<String, Individual> learningResources = new HashMap<>();
        while (learningResourceIterator.hasNext()) {
            Row currentRow = learningResourceIterator.next();

            String id = currentRow.getCell(0).getStringCellValue();
            String author = currentRow.getCell(1).getStringCellValue();
            String[] coursesIds = currentRow.getCell(2).getStringCellValue().split(";");
            String[] learningOutcomeIds = currentRow.getCell(3).getStringCellValue().split(";");
            int difficultyLevel = (int) currentRow.getCell(4).getNumericCellValue();
            String format = currentRow.getCell(5).getStringCellValue();

            Individual individual = learningResource.createIndividual(ACM_URI_PREFIX + id);
            individual.addLiteral(authorProperty,  om.createTypedLiteral(author));
            individual.addLiteral(difficultyLevelProperty,  om.createTypedLiteral(difficultyLevel));
            individual.addLiteral(formatProperty,  om.createTypedLiteral(format));

            for (String cId : coursesIds) {
                Individual courseIndividual = courses.get(cId);
                courseIndividual.addProperty(isTaughtUsing, individual);
            }

            for (String loId : learningOutcomeIds) {
                Individual learningOutcomeIndividual = learningOutcomes.get(loId);
                learningOutcomeIndividual.addProperty(obtainedBy, individual);
            }

            learningResources.put(id, individual);
        }

    }
}
