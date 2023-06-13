package com.noggong.example.batch.config

import mu.KotlinLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private val log = KotlinLogging.logger {}

@Configuration
@ConditionalOnProperty(name = ["job.name"], havingValue = "singleStepJob")
class SingleStepJobConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {
    @Bean
    fun singleStepJob(): Job {
        return jobBuilderFactory.get("singleStepJob")
            .start(singleStep())
            .build()
    }

    @Bean
    fun singleStep(): Step {
        return stepBuilderFactory.get("singleStep")
            .tasklet {_: StepContribution, _: ChunkContext ->
                log.info { "Single Step!!" }
                RepeatStatus.FINISHED
            }
            .build()
    }
}