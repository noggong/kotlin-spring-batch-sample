package com.noggong.example.batch.config

import mu.KotlinLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private val log = KotlinLogging.logger {}

@Configuration
@ConditionalOnProperty(name = ["job.name"], havingValue = "otherJob")
class OtherJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {
    @Bean
    fun otherJob(): Job {
        return jobBuilderFactory.get("otherJob")
            .start(otherStep())
            .build()
    }

    fun otherStep(): Step {
        return stepBuilderFactory.get("otherStep")
            .tasklet {_, _ ->
                log.info { ">>>> other job started >>>>" }
                RepeatStatus.FINISHED
            }
            .build()
    }

}