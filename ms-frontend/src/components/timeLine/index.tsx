import { Link } from 'react-router-dom';
import { InnerCircle, Line, Step, TimelineWrapper } from './style';
import { FiEdit3 } from 'react-icons/fi';
import React from 'react';

type TimelineProps = {
  currentStep: number;
};

export function Timeline({ currentStep }: TimelineProps) {
  const steps = [
    { name: 'Retirada/Devolução', url: '' },
    { name: 'Grupos', url: 'reservation/group' },
    { name: 'Adicionais', url: 'reservation/additional' },
    { name: 'Confirmação', url: 'checkout' },
  ];

  return (
    <TimelineWrapper>
      {steps.map((step, index) => (
        <React.Fragment key={index}>
          <Step key={index} isActive={index === currentStep} isCompleted={index < currentStep}>
            <InnerCircle isCompleted={index < currentStep} />
            <p>
              {index < currentStep ? (
                <Link to={`/${step.url}`}>
                  {step.name} 
                  <FiEdit3 />
                </Link>
              ) : (
                step.name
              )}
            </p>
          </Step>
          {index < steps.length - 1 && <Line isActive={index < currentStep} />}
        </React.Fragment>
      ))}
    </TimelineWrapper>
  );
}