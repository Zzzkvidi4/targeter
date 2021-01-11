namespace motivator.Motivator
{
    public interface IMotivator
    {
        public void Start();
        public void Rerun(long targetId);
    }
}