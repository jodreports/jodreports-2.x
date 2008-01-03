//
// JOOReports - The Open Source Java/OpenOffice Report Engine
// Copyright (C) 2004-2006 - Mirko Nasato <mirko@artofsolving.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
// http://www.gnu.org/copyleft/lesser.html
//
package net.sf.jooreports.web.samples;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import net.sf.jooreports.templates.images.ByteArrayImageProvider;
import net.sf.jooreports.templates.images.ImageProvider;
import net.sf.jooreports.web.spring.controller.AbstractDocumentGenerator;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class SalesReportGenerator extends AbstractDocumentGenerator {

    public static class ReportLine {
        private String month;
        private int value;
        
        public ReportLine(String month, int value) {
            this.month = month;
            this.value = value;
        }

        public String getMonth() {
            return month;
        }

        public int getValue() {
            return value;
        }
    }

    protected Object getModel(HttpServletRequest request) {
        List lines = new ArrayList();
        DateFormat monthFormat = new SimpleDateFormat("MMM");
        Calendar calendar = Calendar.getInstance();
        for (int month = 1; month <= 6; month++) {
            String value = request.getParameter("month_"+ month);
            calendar.set(Calendar.MONTH, month - 1);
            lines.add(new ReportLine(monthFormat.format(calendar.getTime()), Integer.parseInt(value)));
        }
        Map model = new HashMap();
        model.put("lines", lines);
        return model;
    }

    protected ImageProvider getImageProvider(Object model) {
    	ByteArrayImageProvider imageProvider = new ByteArrayImageProvider();
    	imageProvider.setImage("chart", createChart(model));
    	return imageProvider;
    }

    private byte[] createChart(Object model) {
        List lines = (List) ((Map) model).get("lines");
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Iterator it = lines.iterator(); it.hasNext();) {
            ReportLine line = (ReportLine) it.next();
            dataset.addValue(line.getValue(), "sales", line.getMonth());
        }
        JFreeChart chart = ChartFactory.createBarChart("Monthly Sales", "Month", "Sales", dataset, PlotOrientation.VERTICAL, false, false, false);
        chart.setTitle((String)null);
        chart.setBackgroundPaint(Color.white);
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        GradientPaint paint = new GradientPaint(
            0.0f, 0.0f, Color.blue,
            0.0f, 0.0f, new Color(0, 0, 64)
        );
        renderer.setSeriesPaint(0, paint);
        BufferedImage image = chart.createBufferedImage(400, 300);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
			ImageIO.write(image, "png", outputStream);
		} catch (IOException ioException) {
			throw new RuntimeException("should never happen: " + ioException.getMessage());
		}
		return outputStream.toByteArray();
    }
}
