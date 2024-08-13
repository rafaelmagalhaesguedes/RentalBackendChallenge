import styled from 'styled-components';

export const TimelineWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-around;
  margin-top: 8rem;
  position: relative;
  width: 80%;
`;

export const Line = styled.div<{ isActive: boolean }>`
  flex-grow: 1;
  height: ${props => (props.isActive ? '3px' : '1px')};
  background-color: ${props => (props.isActive ? '#007bff' : '#ddd')};
`;

export const Step = styled.div<{ isCompleted: boolean, isActive: boolean }>`
  padding: 10px;
  border: ${props => (props.isActive ? 'none' : '1px solid #ddd')};
  border-radius: 50%;
  width: 20px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: ${props => (props.isActive ? '#007bff' : (props.isCompleted ? '#007bff' : '#fff'))};
  position: relative;
  z-index: 1;

  p {
    font-size: 13px;
    color: ${props => (props.isActive ? '#007bff' : '#000')};
    margin-top: 4rem;
    color: ${props => (props.isCompleted ? '#007bff' : '#666')};
    font-family: 'Lexend Deca', sans-serif;
  }

  a {
    display: flex;
    align-items: center;
    gap: 0.5rem;
  }
`;

export const InnerCircle = styled.div<{ isCompleted: boolean }>`
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background: ${props => (props.isCompleted ? '#007bff' : '#fff')};
`;