import Button from '@mui/material/Button'
import Card from '@mui/material/Card'
import CardActions from '@mui/material/CardActions'
import CardContent from '@mui/material/CardContent'
import CardMedia from '@mui/material/CardMedia'
import Typography from '@mui/material/Typography'
import Link from 'next/link'

type Props = {}

export const CardItem = (props: Props) => {
	return (
		<>
			<Card sx={{ maxWidth: 345 }}>
				<CardMedia component="img" alt="photo" height="300" image="https://picsum.photos/200/300" />
				<CardContent>
					<Link href="/client">
						<Typography gutterBottom variant="h5" component="div">
							Lizards are a widespread group of squamate reptiles, wit
						</Typography>
					</Link>
					<Typography variant="h6" color="text.secondary">
						20000 VND
					</Typography>
				</CardContent>
				<CardActions>
					<Button size="small">Share</Button>
					<Button size="small">Learn More</Button>
				</CardActions>
			</Card>
		</>
	)
}
