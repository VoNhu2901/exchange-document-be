import Link from 'next/link'
import styles from '../../styles/Home.module.css'
type Props = {}

export const Header = (props: Props) => {
	return (
		<>
			<header className={styles.header}>
				<div className={styles.container}>
					<h1 className={styles.logo}>Exchange SGU</h1>
					<div className={styles.right}>
						<div className={styles.menu}>
							<ul style={{ listStyle: 'none' }}>
								<li>
									<Link href="/" className={styles.link}>
										Home
									</Link>
								</li>
								<li style={{ float: 'left' }}>
									<Link href="/client" className={styles.link}>
										Client
									</Link>
								</li>
								<li>
									<Link href="/blog/hello-world" className={styles.link}>
										Blog Post
									</Link>
								</li>
							</ul>
						</div>
						<div className={styles.search}></div>
					</div>
				</div>
			</header>
		</>
	)
}
