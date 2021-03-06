/**
 * Copyright (c) 2014-2015, FrontEndART Software Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    This product includes software developed by FrontEndART Software Ltd.
 * 4. Neither the name of FrontEndART Software Ltd. nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY FrontEndART Software Ltd. ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL FrontEndART Software Ltd. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.sonar.plugins.SourceMeterCore.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.sonar.api.measures.Metric;
import org.sonar.api.measures.MetricFinder;

/**
 * Metric finder class for get SourceMeter Metric objects
 */
public abstract class SourceMeterMetricFinder implements MetricFinder {

    protected final Map<String, Metric> metricMap = new TreeMap<String, Metric>();

    /**
     * Default Constructor
     */
    public SourceMeterMetricFinder() {
        SourceMeterCoreMetrics cm = new SourceMeterCoreMetrics();
        List<Metric> metricList = cm.getMetrics();
        for (Metric metric : metricList) {
            metricMap.put(metric.getKey(), metric);
        }
    }

    /**
     * Returns the language specific Ruleset metrics.
     * 
     * @return
     */
    public abstract List<Metric> findLanguageSpecificRulesetMetrics();

    /**
     * Find a SourceMeter Metric by ID
     * 
     * @param id
     * @return Metric
     */
    @Override
    public Metric findById(int id) {
        Collection<Metric> metrics = metricMap.values();
        for (Metric metric : metrics) {
            if (metric.getId() == id) {
                return metric;
            }
        }
        return null;
    }

    /**
     * Find a SourceMeter Metric by key (example: "SM:LOC")
     * 
     * @param key (short name)
     * @return Metric
     */
    @Override
    public Metric findByKey(String key) {
        return metricMap.get(key);
    }

    /**
     * Find a list of SourceMeter metrics by keys
     * 
     * @param List of metric keys
     * @return Collection of Metrics
     */
    @Override
    public Collection<Metric> findAll(List<String> metricKeys) {
        List<Metric> retValue = new ArrayList<Metric>();
        for (String key : metricKeys) {
            Metric tmp = findByKey(key);
            if (tmp != null) {
                retValue.add(tmp);
            }
        }
        return retValue;
    }

    /**
     * Gives back all of the SourceMeter metrics
     * 
     * @return Collection of Metrics
     */
    @Override
    public Collection<Metric> findAll() {
        return metricMap.values();
    }
}
